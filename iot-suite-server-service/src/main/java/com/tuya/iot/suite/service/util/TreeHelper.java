package com.tuya.iot.suite.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description permission template util
 * @date 2021/06/02
 */
@Getter
public class TreeHelper<T, N> {
    private Class<N> nodeClass;
    private String codeName;
    private String parentCodeName;
    private String childrenName;

    private Class<T> codeClass;
    private Field codeFiled;
    private Field parentCodeField;
    private Field childrenField;

    public TreeHelper(String codeName, String parentCodeName, String childrenName, Class<N> nodeClass) {
        this.codeName = codeName;
        this.parentCodeName = parentCodeName;
        this.childrenName = childrenName;
        this.nodeClass = nodeClass;
        init();
    }

    private Class<N> nodeClass() {
        return nodeClass;
    }

    @SneakyThrows
    private N newTreeNode() {
        return nodeClass.newInstance();
    }

    public TreeHelper init() {
        codeFiled = ReflectionUtils.findField(nodeClass, codeName);
        if (!codeFiled.isAccessible()) {
            codeFiled.setAccessible(true);
        }
        parentCodeField = ReflectionUtils.findField(nodeClass, parentCodeName);
        if (!parentCodeField.isAccessible()) {
            parentCodeField.setAccessible(true);
        }
        childrenField = ReflectionUtils.findField(nodeClass, childrenName);
        if (!childrenField.isAccessible()) {
            childrenField.setAccessible(true);
        }
        codeClass = (Class<T>) codeFiled.getDeclaringClass();
        return this;
    }

    @SneakyThrows
    private TreeHelper code(N target, T code) {
        codeFiled.set(target, code);
        return this;
    }

    @SneakyThrows
    private T code(N target) {
        return (T) codeFiled.get(target);
    }

    @SneakyThrows
    private TreeHelper parentCode(N target, T parentCode) {
        parentCodeField.set(target, parentCode);
        return this;
    }

    @SneakyThrows
    private T parentCode(N target) {
        return (T) parentCodeField.get(target);
    }

    @SneakyThrows
    private TreeHelper children(N target, List<N> children) {
        childrenField.set(target, children);
        return this;
    }

    @SneakyThrows
    private List<N> children(N target) {
        return (List<N>) childrenField.get(target);
    }

    public N dummyRoot(T dummyRootCode,List<N> nodes) {
        nodes = JSONArray.parseArray(JSON.toJSONString(nodes), nodeClass());
        N dummyRoot = newTreeNode();
        code(dummyRoot, dummyRootCode);
        children(dummyRoot, nodes);
        nodes.forEach(node -> parentCode(node, dummyRootCode));
        return dummyRoot;
    }

    public List<N> flatten(N root) {
        N tree = JSONObject.parseObject(JSON.toJSONString(root), nodeClass());
        List<N> flatten = new ArrayList<>();
        bfs(tree, node -> flatten.add(node));
        flatten.forEach(it->children(it,null));
        return flatten;
    }

    public void bfs(N tree, Consumer<N> consumer) {
        //检测循环引用
        Set<T> acceptedCodes = new HashSet<>();
        LinkedList<N> queue1 = new LinkedList<>();
        LinkedList<N> queue2 = new LinkedList<>();
        queue1.add(tree);
        N node;
        List<N> children;
        while (!queue1.isEmpty()) {
            node = queue1.poll();
            if(!acceptedCodes.add(code(node))){
                throw new RuntimeException("multiple code: "+code(node)+", maybe there is circular reference!");
            }
            children = children(node);
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
        dfsWithPreOrder0(root,consumer, new HashSet<>());
    }
    private void dfsWithPreOrder0(N root, Consumer<N> consumer,Set<T> visitedCodes) {
        if(!visitedCodes.add(code(root))){
            throw new RuntimeException("multiple code: "+code(root)+", maybe there is circular reference!");
        }
        List<N> children = children(root);
        consumer.accept(root);
        if (children != null) {
            for (N child : children) {
                dfsWithPreOrder0(child, consumer, visitedCodes);
            }
        }
    }

    public void dfsWithPostOrder(N root, Consumer<N> consumer) {
        dfsWithPostOrder0(root,consumer,new HashSet<>());
    }
    private void dfsWithPostOrder0(N root, Consumer<N> consumer,Set<T> acceptedCodes) {
        if(!acceptedCodes.add(code(root))){
            throw new RuntimeException("multiple code: "+code(root)+", maybe there is circular reference!");
        }
        List<N> children = children(root);
        if (children != null) {
            for (N child : children) {
                dfsWithPostOrder0(child, consumer,acceptedCodes);
            }
        }
        consumer.accept(root);
    }

    public void bfsByLevel(N root, Consumer<List<N>> consumer) {
        Set<T> visitedCodes = new HashSet<>();
        List<N> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            for (N n : queue) {
                if(!visitedCodes.add(code(n))){
                    throw new RuntimeException("multiple code: "+code(root)+", maybe there is circular reference!");
                }
            }
            consumer.accept(queue);
            queue = queue.stream().filter(it -> children(it) != null)
                    .flatMap(it -> children(it).stream())
                    .collect(Collectors.toList());
        }
    }


    public N treeify(List<N> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        //copy
        nodes = JSONArray.parseArray(JSON.toJSONString(nodes), nodeClass());
        //code=>node
        Map<T, N> map = nodes.stream().collect(Collectors.toMap(it -> code(it), it -> it));
        //code=>children
        Map<T, List<N>> childrenMap = nodes.stream().filter(it -> parentCode(it) != null)
                .collect(Collectors.groupingBy(it -> parentCode(it)));
        //find roots, which parentCode not in map
        List<N> roots = nodes.stream().filter(it -> parentCode(it) == null || !map.containsKey(code(it)))
                .collect(Collectors.toList());
        if (roots.size() != 1) {
            throw new RuntimeException("error! can't treeify nodes, cause roots.size()==" + roots.size());
        }
        //set children
        nodes.forEach(it -> children(it, childrenMap.get(code(it))));
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
        //code=>node
        Map<T, N> map = new HashMap<>();
        nodes.forEach(it -> map.put(code(it), it));

        //find roots, which parentCode not in map
        List<N> roots = map.values().stream().filter(it ->
                parentCode(it) == null || !map.containsKey(code(it)))
                .collect(Collectors.toList());
        if (roots.size() != 1) {
            throw new RuntimeException("error! can't treeify nodes, cause roots.size()==" + roots.size());
        }
        //code=>children
        Map<T, List<N>> childrenMap = map.values().stream().filter(it -> parentCode(it) != null)
                .collect(Collectors.groupingBy(it -> parentCode(it)));
        //set children
        map.values().forEach(it -> children(it, childrenMap.get(code(it))));
        return roots.get(0);
    }

    public N mergeTrees(N oldTree, N newTree) {
        List<N> list1 = flatten(oldTree);
        List<N> list2 = flatten(newTree);
        list1.addAll(list2);
        return treeifyEnableDuplicateCode0(list1);
    }


}
