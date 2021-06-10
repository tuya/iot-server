package com.tuya.iot.suite.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.tuya.iot.suite.model.Area
import com.tuya.iot.suite.service.util.TreeHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/10
 */
@Slf4j
class TreeHelperSpec extends Specification{
    def json = """
{
    "code":"/",
    "parentCode":null,
    "children":[{
        "code":"/a/",
        "parentCode":"/",
        "children":[{
            "code":"/a/a/",
            "parentCode":"/a/",
            "children":[]
        }]
    },{
        "code":"/b/",
        "parentCode":"/",
        "children":[{
            "code":"/b/a/",
            "parentCode":"/b/"
        }]
    }]
}
"""
    def json2 = """
{
    "code":"/",
    "parentCode":null,
    "children":[{
        "code":"/a/",
        "parentCode":"/",
        "children":[{
            "code":"/a/b/",
            "parentCode":"/a/"
        }]
    },{
        "code":"/c/",
        "parentCode":"/",
        "children":[{
            "code":"/c/a/",
            "parentCode":"/c/"
        }]
    }]
}
"""
    TreeHelper<String,Area> helper = new TreeHelper('code','parentCode','children',Area)

    void testBfs(){
        given:
        def area = JSONObject.parseObject(json, Area)
        when:
        helper.bfs(area){
            println it.code
        }
        then:
        noExceptionThrown()
    }

    void "testFlatten"(){
        given:

        def area = JSONObject.parseObject(json, Area)
        when:
        List<Area> list = helper.flatten(area)
        list.each{
            Area it->
            it.setChildren(null)
            println it
        }
        then:
        list.size() > 1
    }

    void testBfsReversed(){
        given:
        def area = JSONObject.parseObject(json, Area)
        when:
        helper.bfsReversed(area){
            println it
        }
        then:
        noExceptionThrown()
    }

    void testDfsWithPreOrder(){
        given:
        def area = JSONObject.parseObject(json, Area)
        when:
        helper.dfsWithPreOrder(area){
            println it.code
        }
        then:
        noExceptionThrown()
    }

    void testDfsWithPostOrder(){
        given:
        def area = JSONObject.parseObject(json, Area)
        when:
        helper.dfsWithPostOrder(area){
            println it.code
        }
        then:
        noExceptionThrown()
    }
    void testBfsByLevel(){
        given:
        def area = JSONObject.parseObject(json, Area)
        when:
        helper.bfsByLevel(area){
            println it.collect{Area a->a.getCode()}
        }
        then:
        noExceptionThrown()
    }

    void testTreeify(){
        given:
        def area = JSONObject.parseObject(json, Area)
        List<Area> list = helper.flatten(area)
        list.each {
            println it.code
        }
        when:
        def tree = helper.treeify(list)
        println JSON.toJSONString(tree,true)
        then:
        noExceptionThrown()
    }

    void testTreeifyEnableDuplicateCode(){
        given:
        def area = JSONObject.parseObject(json, Area)
        List<Area> list = helper.flatten(area)
        list.add(list[0])
        list.each {
            println it.code
        }
        when:
        def tree = helper.treeifyEnableDuplicateCode(list)
        println JSON.toJSONString(tree,true)
        then:
        noExceptionThrown()
    }

    void testMergeTree(){
        given:
        def area = JSONObject.parseObject(json, Area)
        def area2 = JSONObject.parseObject(json2, Area)
        when:
        def newArea = helper.mergeTrees(area,area2)
        println JSON.toJSONString(newArea,true)
        then:
        noExceptionThrown()
    }

    void testDummyRoot(){
        given:
        def area = JSONObject.parseObject(json, Area)

        when:
        Area root = helper.dummyRoot('+',area.getChildren())
        helper.bfs(root){
            Area it->
            println it.code
        }
        then:
        noExceptionThrown()
    }
    void testCircleRef(){
        given:
        def area = JSONObject.parseObject(json, Area)
        area.children[0].children = [area]
        println JSON.toJSONString(area)
        when:
        helper.bfs(area){
            println it.code
        }
        then:
        def err = thrown(RuntimeException)
        check(err)
    }
    def check(err){
        println err
        true
    }
}
