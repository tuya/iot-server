package com.tuya.iot.suite.web.connector;

import com.tuya.iot.suite.ability.idaas.connector.SpaceConnector;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyResp;
import com.tuya.iot.suite.web.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author benguan
 */
@Slf4j
public class SpaceConnectorTest extends BaseTest {

    @Value("${project.code}")
    String projectCode;
    @Autowired
    private SpaceConnector spaceConnector;

    @Test
    public void testApplySpace() {
        SpaceApplyResp res = spaceConnector.applySpace(SpaceApplyReq.builder()
                .spaceCode(projectCode)
                .spaceGroup("tuya")
                .remark("none")
                .build());
        log.info("<===", res);
    }
}
