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
        Assert.assertTrue(RoleTypeEnum.admin.isOffspringOrSelf(RoleTypeEnum.admin));
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelf(RoleTypeEnum.manage));
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.manage.isOffspringOrSelf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.manage.isOffspringOrSelf(RoleTypeEnum.manage));
        Assert.assertFalse(RoleTypeEnum.manage.isOffspringOrSelf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelf(RoleTypeEnum.manage));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelf(RoleTypeEnum.normal));
    }
}
