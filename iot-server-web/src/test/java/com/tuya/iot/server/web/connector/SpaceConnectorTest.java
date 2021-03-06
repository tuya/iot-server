package com.tuya.iot.server.web.connector;

import com.tuya.connector.open.common.util.Sha256Util;
import com.tuya.iot.server.ability.idaas.connector.SpaceConnector;
import com.tuya.iot.server.ability.idaas.model.SpaceApplyReq;
import com.tuya.iot.server.web.BaseTest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
        String res = spaceConnector.applySpace(SpaceApplyReq.builder()
                .spaceCode(projectCode)
                .spaceGroup("tuya")
                .remark("none")
                .build());
        log.info("<===", res);
    }

    @Test
    @SneakyThrows
    public void test(){
        System.out.println(Sha256Util.encryption("ty1198094110"));
    }
}
