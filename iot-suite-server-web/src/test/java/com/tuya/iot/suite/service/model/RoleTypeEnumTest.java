package com.tuya.iot.suite.service.model;

import com.tuya.iot.suite.service.enums.RoleTypeEnum;
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
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelfOf(RoleTypeEnum.manager));
        Assert.assertFalse(RoleTypeEnum.admin.isOffspringOrSelfOf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.manager.isOffspringOrSelfOf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.manager.isOffspringOrSelfOf(RoleTypeEnum.manager));
        Assert.assertFalse(RoleTypeEnum.manager.isOffspringOrSelfOf(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelfOf(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelfOf(RoleTypeEnum.manager));
        Assert.assertTrue(RoleTypeEnum.normal.isOffspringOrSelfOf(RoleTypeEnum.normal));
    }
}
