package com.tuya.iot.suite.service.model;

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
        Assert.assertTrue(RoleTypeEnum.admin.isOffspringOrSelfOf(RoleTypeEnum.admin));
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelfOf(RoleTypeEnum.manage));
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelfOf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.manage.isOffspringOrSelfOf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.manage.isOffspringOrSelfOf(RoleTypeEnum.manage));
        Assert.assertFalse(RoleTypeEnum.manage.isOffspringOrSelfOf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelfOf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelfOf(RoleTypeEnum.manage));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelfOf(RoleTypeEnum.normal));
    }
}
