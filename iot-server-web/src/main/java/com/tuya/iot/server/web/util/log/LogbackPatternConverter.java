package com.tuya.iot.server.web.util.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Map;

public class LogbackPatternConverter extends ClassicConverter {
    private static final String defaultTid = "N/A";
    public LogbackPatternConverter() {
    }

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        Map<String,String> map = iLoggingEvent.getMDCPropertyMap();
        String tid = defaultTid;
        if(map != null){
            tid = map.getOrDefault(Constant.TRACE_ID_KEY,defaultTid);
        }
        return "TID: "+tid;
    }
}
