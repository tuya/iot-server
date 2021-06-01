package com.tuya.iot.suite.web.config;

import org.apache.shiro.util.AntPathMatcher;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
public class AntPathMatcherTest {
    @Test
    public void testAntPathMatcher(){
        AntPathMatcher matcher = new AntPathMatcher();
        Assert.assertTrue(matcher.match("/*.html","/swagger.html"));
        Assert.assertFalse(matcher.match("*.html","/swagger.html"));
        Assert.assertTrue(matcher.match("/**/*.html","/swagger.html"));
        Assert.assertTrue(matcher.match("/**/*.html","/1/2/3/swagger.html"));
        Assert.assertFalse(matcher.match("/*.html","/1/2/3/swagger.html"));
    }
}
