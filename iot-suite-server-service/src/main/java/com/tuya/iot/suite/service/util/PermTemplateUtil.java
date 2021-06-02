package com.tuya.iot.suite.service.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/02
 */
public abstract class PermTemplateUtil {
    @SneakyThrows
    public static List<PermissionCreateReq> loadPermissionsFromTemplate(String path) {
        File file = ResourceUtils.getFile(path);
        String json = StreamUtils.copyToString(new FileInputStream(file), StandardCharsets.UTF_8);
        JSONObject root = JSONObject.parseObject(json);
        JSONArray menuPerms = root.getJSONArray("permissionList");
        return menuPerms.stream().flatMap(menuPermObj -> {
            JSONObject menuPerm = (JSONObject) menuPermObj;
            JSONArray arr = menuPerm.getJSONArray("permissionList");
            arr.add(menuPerm);
            return arr.stream();
        }).map(perm -> {
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
}
