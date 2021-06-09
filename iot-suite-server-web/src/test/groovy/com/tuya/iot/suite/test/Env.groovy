package com.tuya.iot.suite.test

import com.tuya.connector.open.common.constant.TuyaRegion

/**
 * @description TuyaRegion中并没有定义日常环境，所以，通过反射修改apiUrl
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
class Env {
    static final String DAILY_CN = 'https://openapi-daily.tuya-inc.cn'
    static final String PRE_CN = 'https://openapi-cn.wgine.com'
    static void use(String apiUrl = TuyaRegion.CN.apiUrl){
        def apiUrlField = TuyaRegion.declaredFields.find{
            it.name == 'apiUrl'
        }
        apiUrlField.accessible = true
        //设置环境为开发环境
        TuyaRegion.values().each{
            apiUrlField.set(it,apiUrl)
        }
    }
    static void useDailyCn(){
        use(DAILY_CN)
    }
    static void usePreCn(){
        use(PRE_CN)
    }
}
