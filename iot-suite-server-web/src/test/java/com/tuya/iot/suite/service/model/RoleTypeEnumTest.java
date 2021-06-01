package com.tuya.iot.suite.service.model;

import jdk.nashorn.internal.AssertsEnabled;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
@Slf4j
public class RoleTypeEnumTest {
    @Test
    public void testIsOffspringOrSelf() {
        Assert.assertTrue(RoleTypeEnum.sysadmin.isOffspringOrSelf(RoleTypeEnum.sysadmin));
        Assert.assertFalse(RoleTypeEnum.sysadmin.isOffspringOrSelf(RoleTypeEnum.admin));
        Assert.assertFalse(RoleTypeEnum.sysadmin.isOffspringOrSelf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.admin.isOffspringOrSelf(RoleTypeEnum.sysadmin));
        Assert.assertTrue(RoleTypeEnum.admin.isOffspringOrSelf(RoleTypeEnum.admin));
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelf(RoleTypeEnum.sysadmin));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelf(RoleTypeEnum.normal));
    }
}
