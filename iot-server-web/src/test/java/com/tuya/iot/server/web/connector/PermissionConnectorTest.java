package com.tuya.iot.server.web.connector;

import com.alibaba.fastjson.JSON;
import com.tuya.iot.server.ability.idaas.connector.PermissionConnector;
import com.tuya.iot.server.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.server.web.BaseTest;
import lombok.TextBlock;
import lombok.TextBlocks;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author benguan
 */
@Slf4j
public class PermissionConnectorTest extends BaseTest {

    @Value("${project.permission-space-id}")
    String spaceId;
    @Autowired
    private PermissionConnector permissionConnector;

    @Test
    public void testApplySpace() {
        /*
         {
        "permissionCode": "权限标识",
        "type": "BUTTON",
        "remark": "备注"
        }
        */
        @TextBlock String json = TextBlocks.lazyInit();

        PermissionCreateReq request = JSON.parseObject(json, PermissionCreateReq.class);
        Boolean res = permissionConnector.createPermission(spaceId, request);
        log.info("<===", res);
    }
}
