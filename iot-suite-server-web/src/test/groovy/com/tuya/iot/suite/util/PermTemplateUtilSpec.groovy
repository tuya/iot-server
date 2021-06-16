package com.tuya.iot.suite.util

import com.alibaba.fastjson.JSON
import com.tuya.iot.suite.service.util.PermTemplateUtil
import groovy.util.logging.Slf4j
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/03
 */
@Slf4j
class PermTemplateUtilSpec extends Specification {
    void "测试加载权限模版为列表"() {
        given:
        when:
        def trees = PermTemplateUtil.loadAsFlattenList("classpath:template/permissions_zh.json"){
            true
        }
        log.info(JSON.toJSONString(trees, true))
        then:
        trees.collect { it.permissionCode }
                .containsAll(['1000', '1001', '2001', '2000'])
    }

    void "测试加载权限模版为请求列表"() {
        given:
        when:
        def list = PermTemplateUtil.loadAsPermissionCreateReqList("classpath:template/permissions_zh.json"){
            true
        }
        log.info(JSON.toJSONString(list, true))
        then:
        list.collect {
            it.permissionCode
        }.containsAll(['1000', '1001', '2000', '2001'])
    }

    void "测试权限bfs"(){
        when:
        def trees = PermTemplateUtil.loadAsFlattenList("classpath:template/permissions_zh.json"){
            true
        }
        PermTemplateUtil.bfs(trees){
            println it
        }
        then:
        noExceptionThrown()
    }

    void "测试dfs先序遍历"(){
        when:
        def trees = PermTemplateUtil.loadTrees("classpath:template/permissions_en.json"){
            true
        }
        PermTemplateUtil.dfsWithPreOrder(trees){
            println it
        }
        then:
        noExceptionThrown()
    }

    void "测试dfs后序遍历"(){
        when:
        def root = PermTemplateUtil.load("classpath:template/permissions_zh.json")
        PermTemplateUtil.dfsWithPostOrder(root){
            println it
        }
        then:
        noExceptionThrown()
    }

    void "测试bfs按层级遍历"(){
        when:
        def root = PermTemplateUtil.load("classpath:template/permissions_zh.json")
        PermTemplateUtil.bfsByLevel(root){
            println it.collect{
                node->
                    node.permissionName
            }
        }
        then:
        noExceptionThrown()
    }
    void "测试合并树"(){
        when:
        def trees1 = PermTemplateUtil.loadTrees("classpath:template/permissions_zh.json"){
            true
        }
        def trees2 = PermTemplateUtil.loadTrees("classpath:template/permissions_zh_new.json"){
            true
        }
        def trees = PermTemplateUtil.mergeTrees(trees1,trees2)
        println "trees1===>"
        PermTemplateUtil.dfsWithPreOrder(trees1){
            println it
        }
        println "trees2===>"
        PermTemplateUtil.dfsWithPreOrder(trees2){
            println it
        }
        println "trees===>"
        PermTemplateUtil.dfsWithPreOrder(trees){
            println it
        }
        then:
        noExceptionThrown()
    }
}