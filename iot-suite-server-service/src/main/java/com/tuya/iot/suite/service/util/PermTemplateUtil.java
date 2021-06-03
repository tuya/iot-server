package com.tuya.iot.suite.service.util;

import com.alibaba.fastjson.JSONObject;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
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
 * @description permission template util
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
public abstract class PermTemplateUtil {

    public static List<PermissionCreateReq> loadAsPermissionCreateReqList(String path) {
        return loadAsList(path).stream().map(it->convertToPermissionCreateReq(it)).collect(Collectors.toList());
    }

    public static PermissionCreateReq convertToPermissionCreateReq(PermissionNodeDTO it){
        return PermissionCreateReq.builder()
                .permissionCode(it.getPermissionCode())
                .name(it.getPermissionName())
                .parentCode(it.getParentCode())
                .remark(it.getRemark())
                .type(PermissionTypeEnum.valueOf(it.getPermissionType()))
                .order(it.getOrder())
                .build();
    }
    /**
     * transform tree to list, then set children to null.
     * */
    public static List<PermissionNodeDTO> loadAsList(String path) {
        PermissionNodeDTO root = load(path);
        List<PermissionNodeDTO> flatten = new ArrayList<>();
        LinkedList<PermissionNodeDTO> queue1 = new LinkedList<>(root.getChildren());
        LinkedList<PermissionNodeDTO> queue2 = new LinkedList<>();
        PermissionNodeDTO node;
        List<PermissionNodeDTO> children;
        while(!queue1.isEmpty()){
            node = queue1.poll();
            flatten.add(node);
            children = node.getChildren();
            node.setChildren(null);
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
    public static PermissionNodeDTO load(String path) {
        File file = ResourceUtils.getFile(path);
        String json = StreamUtils.copyToString(new FileInputStream(file), StandardCharsets.UTF_8);
        return JSONObject.parseObject(json,PermissionNodeDTO.class);
    }
}
