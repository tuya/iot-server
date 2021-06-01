package com.tuya.iot.suite.init;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.model.RoleCodeGenerator;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import com.tuya.iot.suite.web.BaseTest;
import com.tuya.iot.suite.web.config.ProjectProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.shade.org.apache.http.client.HttpClient;
import org.apache.pulsar.shade.org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Test
    public void initialize(){
        String uid = "";
        // apply space
        String spaceGroup = "tuya";
        String authentication = "";
        Long spaceId = spaceAbility.querySpace(spaceGroup,projectProperties.getCode());
        if(spaceId==null){
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
        List<String> allPerms = null;

        // add permissions
        HttpClient httpClient = HttpClients.createDefault();



        // grant role
        Boolean grantRoleResult = grantService.grantRoleToUser(UserGrantRoleReq.builder()
                .roleCode(roleCode)
                .uid(uid)
                .spaceId(spaceId)
                .build());

        // grant perms
        Boolean grantPermsResult = grantService.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode(roleCode)
                .permissionCodes(allPerms)
                .build());

    }

}
