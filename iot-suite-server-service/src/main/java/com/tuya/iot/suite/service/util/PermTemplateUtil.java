package com.tuya.iot.suite.service.util;

import com.alibaba.fastjson.JSONObject;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import lombok.SneakyThrows;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @description permission template util
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
public abstract class PermTemplateUtil {

    public static List<PermissionCreateReq> loadAsPermissionCreateReqList(String path,Predicate<PermissionNodeDTO> predicate) {
        return loadAsFlattenList(path,predicate).stream().map(it->convertToPermissionCreateReq(it)).collect(Collectors.toList());
    }

    public static PermissionCreateReq convertToPermissionCreateReq(PermissionNodeDTO it){
        return PermissionCreateReq.builder()
                .permissionCode(it.getPermissionCode())
                .name(it.getPermissionName())
                .parentCode(it.getParentCode())
                .remark(it.getRemark())
                .type(PermissionTypeEnum.valueOf(it.getPermissionType()).getCode())
                .order(it.getOrder())
                .build();
    }
    /**
     * transform tree to list, then set children to null.
     * */
    public static List<PermissionNodeDTO> loadAsFlattenList(String path,Predicate<PermissionNodeDTO> predicate) {
        List<PermissionNodeDTO> trees = loadTrees(path,predicate);
        List<PermissionNodeDTO> flatten = new ArrayList<>();
        bfs(trees,node->{
           flatten.add(node);
           node.setChildren(null);
        });
        return flatten;
    }

    @SneakyThrows
    private static PermissionNodeDTO load(String path) {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(path);

        InputStream inputStream = resource.getInputStream();
        String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        return JSONObject.parseObject(json,PermissionNodeDTO.class);
    }
    public static List<PermissionNodeDTO> loadTrees(String path, Predicate<PermissionNodeDTO> predicate) {
        PermissionNodeDTO node = load(path);
        filterChildren(node,predicate);
        return node.getChildren();
    }
    private static void filterChildren(PermissionNodeDTO node, Predicate<PermissionNodeDTO> predicate){
        if(node.getChildren()!=null && !node.getChildren().isEmpty()){
            node.setChildren(node.getChildren().stream().filter(it->predicate.test(it)).collect(Collectors.toList()));
            node.getChildren().forEach(child->filterChildren(child,predicate));
        }
    }
    /**
     * openapi那边对权限树的操作有限制。
     * 比如批量新增权限，只能新增叶子节点。不能直接新增一颗树。
     * 所以写一个广度优先遍历方法，逐层处理。
     * */
    public static void bfs(List<PermissionNodeDTO> trees, Consumer<PermissionNodeDTO> consumer){
        LinkedList<PermissionNodeDTO> queue1 = new LinkedList<>(trees);
        LinkedList<PermissionNodeDTO> queue2 = new LinkedList<>();
        PermissionNodeDTO node;
        List<PermissionNodeDTO> children;
        while(!queue1.isEmpty()){
            node = queue1.poll();
            children = node.getChildren();
            consumer.accept(node);
            if(children!=null && !children.isEmpty()){
                queue2.addAll(children);
            }
            if(queue1.isEmpty()){
                queue1 = queue2;
                queue2 = new LinkedList<>();
            }
        }

    }
}
