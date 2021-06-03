package com.tuya.iot.suite.service.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum;
import com.tuya.iot.suite.service.model.PermissionTemplate;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/02
 */
public abstract class PermTemplateUtil {

    public static List<PermissionCreateReq> loadAsPermissionCreateReqList(String path) {
        return loadAsList(path).stream().map(it->convertToPermissionCreateReq(it)).collect(Collectors.toList());
    }

    public static PermissionCreateReq convertToPermissionCreateReq(PermissionTemplate it){
        return PermissionCreateReq.builder()
                .permissionCode(it.getPermissionCode())
                .name(it.getPermissionName())
                .parentCode(it.getParentCode())
                .remark(it.getRemark())
                .type(PermissionTypeEnum.valueOf(it.getType()))
                .order(it.getOrder())
                .build();
    }
    public static List<PermissionTemplate> loadAsList(String path) {
        PermissionTemplate root = load(path);
        List<PermissionTemplate> flatten = new ArrayList<>();
        LinkedList<PermissionTemplate> queue1 = new LinkedList<>();
        LinkedList<PermissionTemplate> queue2 = new LinkedList<>();
        queue1.addAll(root.getPermissionList());
        PermissionTemplate node;
        List<PermissionTemplate> children;
        while(!queue1.isEmpty()){
            node = queue1.poll();
            flatten.add(node);
            children = node.getPermissionList();
            node.setPermissionList(null);
            if(children!=null && !children.isEmpty()){
                queue2.addAll(children);
            }
            if(queue1.isEmpty()){
                queue1 = queue2;
                queue2 = new LinkedList<>();
            }
        }
        return flatten;
    }

    @SneakyThrows
    public static PermissionTemplate load(String path) {
        File file = ResourceUtils.getFile(path);
        String json = StreamUtils.copyToString(new FileInputStream(file), StandardCharsets.UTF_8);
        PermissionTemplate root = JSONObject.parseObject(json,PermissionTemplate.class);
        return root;
    }
}
