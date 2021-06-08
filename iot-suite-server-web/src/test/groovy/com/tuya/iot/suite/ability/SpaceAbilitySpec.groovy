package com.tuya.iot.suite.ability

import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.test.Env
import com.tuya.iot.suite.test.Success
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore

/**
 * @description 该测试用例依赖日常环境
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
@Ignore
class SpaceAbilitySpec extends BaseSpec{
    static{
        Env.useDailyCn()
    }
    @Autowired
    SpaceAbility spaceAbility
    @Autowired
    ProjectProperties projectProperties

    void "测试查询权限空间"(){
        when:
        def space = spaceAbility.querySpace(spaceGroup,spaceCode)
        log.info("space={}",space)
        space.spaceId
        throw new Success()
        then:
        thrown(expected)
        //{"result":{"gmt_create":1622802733234,"gmt_modified":1622802733234,"owner":"wujun","space_code":"wujunCode","space_group":"wujunGroup","space_id":1400762304747802670},"success":true,"t":1623074279971}
        where:
        spaceGroup|spaceCode|expected
        'wujunGroup'|'wujunCode'| Success
        'tuya-iot'|'benguanCode'|Success
        'tuya-iot'|'benguan'|NullPointerException
        'tuya-iot'|'iot-app-smart-office'|Success
    }

    void "测试创建权限空间"(){
        given:
        when:
        def spaceId = spaceAbility.applySpace(SpaceApplyReq.builder()
        .spaceGroup(projectProperties.permissionGroup)
        .spaceCode(projectProperties.permissionSpaceCode)
        .authentication(projectProperties.code)
        .owner(projectProperties.permissionSpaceOwner)
        .remark('test')
                .build())
        then:
        spaceId
        //1402084582307659814
    }
}
