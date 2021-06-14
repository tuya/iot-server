package com.tuya.iot.suite.ability

import com.tuya.iot.suite.ability.asset.ability.AssetAbility
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

/**
 * @description 该测试用例依赖日常环境
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
class AssetAbilitySpec extends BaseSpec{
    @Autowired
    AssetAbility assetAbility
    @Autowired
    ProjectProperties projectProperties

    void "测试获取已授权的权限树"(){
        given:
        when:
        def res = assetAbility.pageListAuthorizedAssets('bsh1623052900346u8pQ',1,100)
        then:
        noExceptionThrown()
    }

}
