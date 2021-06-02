package com.tuya.iot.suite.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.idaas.PermissionService;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.model.RoleCodeGenerator;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import com.tuya.iot.suite.web.BaseTest;
import com.tuya.iot.suite.web.config.ProjectProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.shade.org.apache.http.client.HttpClient;
import org.apache.pulsar.shade.org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
@Slf4j
public class DataInitializer extends BaseTest {

    @Autowired
    SpaceAbility spaceAbility;
    @Autowired
    ProjectProperties projectProperties;
    @Autowired
    RoleService roleService;
    @Autowired
    GrantService grantService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PermissionService permissionService;

    @Test
    public void initialize() {
        String uid = "";
        // apply space
        String spaceGroup = "tuya";
        String authentication = "";
        Long spaceId = spaceAbility.querySpace(spaceGroup, projectProperties.getCode());
        if (spaceId == null) {
            spaceId = spaceAbility.applySpace(SpaceApplyReq.builder()
                    .authentication(authentication)
                    .spaceGroup(spaceGroup)
                    .spaceCode(projectProperties.getCode())
                    .build());
        }

        // add role
        String roleCode = RoleCodeGenerator.generate(RoleTypeEnum.admin);
        Boolean roleCreateResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .uid(uid)
                .roleCode(roleCode)
                .roleName("system administrator")
                .remark("i can do everything")
                .build());

        // add user
        //noop

        // get all permissions
        List<PermissionCreateReq> allPerms = loadAllPerms();

        // add permissions
        Boolean createResult = permissionService.batchCreatePermission(spaceId,allPerms);

        // grant role to user
        Boolean grantRoleResult = grantService.grantRoleToUser(UserGrantRoleReq.builder()
                .roleCode(roleCode)
                .uid(uid)
                .spaceId(spaceId)
                .build());

        // grant perms to role
        Boolean grantPermsResult = grantService.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode(roleCode)
                .permissionCodes(allPerms.stream().map(it->it.getPermissionCode()).collect(Collectors.toList()))
                .build());

    }

    @SneakyThrows
    private List<PermissionCreateReq> loadAllPerms() {
        File file = ResourceUtils.getFile("classpath:permissions.json");
        String json = StreamUtils.copyToString(new FileInputStream(file),StandardCharsets.UTF_8);
        JSONObject root = JSONObject.parseObject(json);
        JSONArray menuPerms = root.getJSONArray("permissionList");
        return menuPerms.stream().flatMap(menuPermObj->{
            JSONObject menuPerm = (JSONObject) menuPermObj;
            JSONArray arr = menuPerm.getJSONArray("permissionList");
            arr.add(menuPerm);
            return arr.stream();
        }).map(perm-> {
            JSONObject it = (JSONObject) perm;
            return PermissionCreateReq.builder()
                    .permissionCode(it.getString("permissionCode"))
                    .parentCode(it.getString("parentCode"))
                    .name(it.getString("permissionName"))
                    .type(PermissionTypeEnum.valueOf(it.getString("type")))
                    .order(it.getInteger("order"))
                    .remark(it.getString("remark"))
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * this only for api permissions
     * */
    private List<PermissionCreateReq> fetchAllPerms() {
        String apiDocsString = restTemplate.getForObject("http://localhost:8080/v2/api-docs", String.class);
        JSONObject docs = JSONObject.parseObject(apiDocsString);

        List<PermissionCreateReq> perms = new ArrayList<>();

        JSONObject paths = docs.getJSONObject("paths");
        paths.forEach((path, methodObjs) -> {
            JSONObject methods = (JSONObject) methodObjs;
            methods.forEach((method,apiObj) -> {
                JSONObject api = (JSONObject) apiObj;
                String perm = Stream.of(path.split("/")).filter(it ->
                        StringUtils.hasText(it)
                ).map(it ->
                        it.matches("\\{.*\\}") ? "*" : it
                ).collect(Collectors.joining(":"));
                perms.add(PermissionCreateReq.builder()
                        .permissionCode(method+":"+perm)
                        .remark(api.getString("summary")).build());
            });
        });

        return perms;
    }

    @Test
    @SneakyThrows
    public void testFetchAllPerms() {
        log.info("====>");
        String json = JSON.toJSONString(fetchAllPerms(),true);
        Files.write(json.getBytes(StandardCharsets.UTF_8),Paths.get("./permission.json").toFile());
        log.info("{}", json);
    }

}
