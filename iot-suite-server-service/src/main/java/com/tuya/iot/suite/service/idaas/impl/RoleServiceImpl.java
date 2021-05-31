package com.tuya.iot.suite.service.idaas.impl;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.service.idaas.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleAbility roleAbility;
    @Override
    public List<IdaasRole> queryRolesByUser(Long spaceId, String userId) {
        //return Lists.newArrayList(IdaasRole.builder().roleCode("sysadmin").build());
        return roleAbility.queryRolesByUser(spaceId,userId);
    }
}
