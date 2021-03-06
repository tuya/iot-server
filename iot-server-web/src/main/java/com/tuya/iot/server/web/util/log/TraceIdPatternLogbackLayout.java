package com.tuya.iot.server.web.util.log;

import ch.qos.logback.classic.PatternLayout;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/04
 */
public class TraceIdPatternLogbackLayout extends PatternLayout {
    public TraceIdPatternLogbackLayout() {
    }

    static {
        defaultConverterMap.put(Constant.TRACE_ID_KEY, LogbackPatternConverter.class.getName());
    }
}
