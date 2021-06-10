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
        Assert.assertTrue(RoleTypeEnum.admin.ltEq(RoleTypeEnum.admin));
        Assert.assertFalse(RoleTypeEnum.admin.ltEq(RoleTypeEnum.manager));
        Assert.assertFalse(RoleTypeEnum.admin.ltEq(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.manager.ltEq(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.manager.ltEq(RoleTypeEnum.manager));
        Assert.assertFalse(RoleTypeEnum.manager.ltEq(RoleTypeEnum.normal));
        Assert.assertTrue(RoleTypeEnum.normal.ltEq(RoleTypeEnum.admin));
        Assert.assertTrue(RoleTypeEnum.normal.ltEq(RoleTypeEnum.manager));
        Assert.assertTrue(RoleTypeEnum.normal.ltEq(RoleTypeEnum.normal));
    }
}
