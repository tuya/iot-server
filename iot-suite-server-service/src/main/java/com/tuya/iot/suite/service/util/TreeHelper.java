package com.tuya.iot.suite.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description permission template util
 * @date 2021/06/02
 */
public class TreeHelper<T, N extends TreeNode<T, N>> {
    private Class<N> nodeClass;

    private TreeHelper() {

    }

    private Class<N> nodeClass() {
        return nodeClass;
    }

    @SneakyThrows
    private N newTreeNode() {
        return nodeClass.newInstance();
    }

    public static <T, N extends TreeNode<T, N>> TreeHelper<T, N> create(Class<N> nodeClass) {
        TreeHelper<T, N> helper = new TreeHelper<>();
        helper.nodeClass = nodeClass;
        return helper;
    }

    public void withDummyRoot(List<N> nodes, T dummyRootCode, Consumer<N> consumer) {
        nodes = JSONArray.parseArray(JSON.toJSONString(nodes), nodeClass());
        N dummyRoot = newTreeNode();
        dummyRoot.code(dummyRootCode);
        dummyRoot.children(nodes);
        nodes.forEach(node -> node.parentCode(dummyRootCode));
        consumer.accept(dummyRoot);
    }

    public List<N> flatten(N root) {
        N tree = JSONObject.parseObject(JSON.toJSONString(root), nodeClass());
        List<N> flatten = new ArrayList<>();
        bfs(tree, node -> flatten.add(node));
        return flatten;
    }

    public void bfs(N tree, Consumer<N> consumer) {
        LinkedList<N> queue1 = new LinkedList<>();
        LinkedList<N> queue2 = new LinkedList<>();
        queue1.add(tree);
        N node;
        List<N> children;
        while (!queue1.isEmpty()) {
            node = queue1.poll();
            children = node.children();
            consumer.accept(node);
            if (children != null) {
                queue2.addAll(children);
            }
            if (queue1.isEmpty()) {
                queue1 = queue2;
                queue2 = new LinkedList<>();
            }
        }
    }

    public void bfsReversed(N tree, Consumer<N> consumer) {
        LinkedList<N> nodes = new LinkedList<>();
        bfs(tree, it -> nodes.add(it));
        while (!nodes.isEmpty()) {
            consumer.accept(nodes.removeLast());
        }
    }

    public void dfsWithPreOrder(N root, Consumer<N> consumer) {
        List<N> children = root.children();
        consumer.accept(root);
        if (children != null) {
            for (N child : children) {
                dfsWithPreOrder(child, consumer);
            }
        }
    }

    public void dfsWithPostOrder(N root, Consumer<N> consumer) {
        List<N> children = root.children();
        if (children != null) {
            for (N child : children) {
                dfsWithPostOrder(child, consumer);
            }
        }
        consumer.accept(root);
    }

    public void bfsByLevel(N root, Consumer<List<N>> consumer) {
        List<N> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            consumer.accept(queue);
            queue = queue.stream().filter(it -> it.children() != null)
                    .flatMap(it -> it.children().stream())
                    .collect(Collectors.toList());
        }
    }


    public N treeify(List<N> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        nodes = JSONArray.parseArray(JSON.toJSONString(nodes), nodeClass());
        //permissionCode=>PermissionNodeDTO
        Map<T, N> map = nodes.stream().collect(Collectors.toMap(it -> it.code(), it -> it));
        //permissionCode=>children
        Map<T, List<N>> childrenMap = nodes.stream().filter(it -> it.parentCode() != null)
                .collect(Collectors.groupingBy(it -> it.parentCode()));
        //find roots, which parentCode not in map
        List<N> roots = nodes.stream().filter(it -> it.parentCode() == null || !map.containsKey(it.code()))
                .collect(Collectors.toList());
        if (roots.size() != 1) {
            throw new RuntimeException("error! can't treeify nodes, cause roots.size()==" + roots.size());
        }
        //set children
        nodes.forEach(it -> it.children(childrenMap.get(it.code())));
        return roots.get(0);
    }

    public N treeifyEnableDuplicateCode(Collection<N> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        nodes = JSONArray.parseArray(JSON.toJSONString(nodes), nodeClass());
        return treeifyEnableDuplicateCode0(nodes);
    }

    private N treeifyEnableDuplicateCode0(Collection<N> nodes) {
        //permissionCode=>PermissionNodeDTO
        Map<T, N> map = new HashMap<>();
        nodes.forEach(it -> map.put(it.code(), it));

        //find roots, which parentCode not in map
        List<N> roots = map.values().stream().filter(it ->
                it.parentCode() == null || !map.containsKey(it.code()))
                .collect(Collectors.toList());
        if (roots.size() != 1) {
            throw new RuntimeException("error! can't treeify nodes, cause roots.size()==" + roots.size());
        }
        //permissionCode=>children
        Map<T, List<N>> childrenMap = map.values().stream().filter(it -> it.parentCode() != null)
                .collect(Collectors.groupingBy(it -> it.parentCode()));
        //set children
        map.values().forEach(it -> it.children(childrenMap.get(it.code())));
        return roots.get(0);
    }

    public N mergeTrees(N oldTree, N newTree) {
        List<N> list1 = flatten(oldTree);
        List<N> list2 = flatten(newTree);
        list1.addAll(list2);
        return treeifyEnableDuplicateCode0(list1);
    }


}
