package com.tuya.iot.suite.ability

import com.tuya.iot.server.ability.asset.ability.AssetAbility
import com.tuya.iot.server.ability.asset.model.AssetAuthBatchToUser
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.server.web.config.ProjectProperties
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

    void "资产授权"(){
        given:
        def uid = 'bay1623838187533bSOz'
        def assetIds = '1384410863863119872,1384554592699764736,1384554605504974848,1384555187057807360,1384802729318219776,1384803394241294336,1384806016071307264,1384855181518233600,1384859173660975104,1384859564360392704,1385251065061998592,1385421056394711040,1385438694349070336,1385481579991715840,1385515582887137280,1385557475981348864,1385940709483286528,1385941288104300544,1385945334915600384,1386211086679732224,1386211824738824192,1386214193530728448,1386214212556091392,1386215027987509248,1386244871030870016,1386244919550578688,1386244950865252352,1386245209150492672,1386245971700125696,1386246121189314560,1386246227389091840,1386247718489329664,1386248250331271168,1386248337027534848,1388422763869396992,1388424222556053504,1395278507067699200,1396643907676192768,1397078705783767040,1401724540693815296'
        assetIds = assetIds.split(',')[0..10].join(',')
        when:
        assetAbility.batchAssetsAuthorizedToUser(uid,new AssetAuthBatchToUser(uid,assetIds,true))
        then:
        noExceptionThrown()
    }

}
