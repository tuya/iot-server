package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.service.idaas.PermissionCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service("permissionCheckService")
@Slf4j
public class PermissionCheckServiceImpl implements PermissionCheckService {


    @Override
    public boolean checkPermissionForUser(Long spaceId, String userId, String permissionCode) {
        if(permissionCode.contains("users")){
            return true;
        }
        return false;
    }
}
