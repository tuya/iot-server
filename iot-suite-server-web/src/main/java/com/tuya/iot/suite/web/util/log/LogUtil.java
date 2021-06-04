package com.tuya.iot.suite.web.util.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * 对于业务异常，我们一般不打印异常栈。
 * 但是没有异常栈，我们如何定位消息从哪里产生的呢？
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/04
 */
@Slf4j
public abstract class LogUtil {
    public static void info(Logger logger,Exception e,String msg,Object... args){
        try{
            Throwable t = e;
            while(t.getCause()!=null){
                t = t.getCause();
            }
            StackTraceElement st = t.getStackTrace()[0];
            String at = " at ";
            at += st.getClassName() + "#" + st.getMethodName() + ":" + st.getLineNumber();
            msg += at;
        }catch(Exception ex){
            log.warn("",ex.getMessage());
        }
        logger.info(msg,args);
    }
}
