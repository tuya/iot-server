package com.tuya.iot.suite.web.config;

import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/02
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IotSuiteServerAppRunner implements ApplicationRunner {
    @Autowired
    ProjectProperties projectProperties;
    @Autowired
    SpaceAbility spaceAbility;
    /**
     *
     * */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // if spaceId has config, use it.
        Long spaceId = projectProperties.getPermissionSpaceId();
        if(spaceId!=null){
            log.info("project.permission-space-id={}",spaceId);
            return;
        }
        // else query spaceId.
        spaceId = spaceAbility.querySpace(projectProperties.getPermissionGroup(),projectProperties.getPermissionSpaceCode());
        if(spaceId!=null){
            projectProperties.setPermissionSpaceId(spaceId);
            log.info("exists spaceId {} at iot-cloud",spaceId);
            return;
        }
        // else apply a spaceId.
        spaceId = spaceAbility.applySpace(SpaceApplyReq.builder()
                .spaceGroup(projectProperties.getPermissionGroup())
                .spaceCode(projectProperties.getPermissionSpaceCode()).build());
        if(spaceId!=null){
            projectProperties.setPermissionSpaceId(spaceId);
            log.info("applied spaceId: {}",spaceId);
            return;
        }
        // else throw a exception.
        throw new RuntimeException("apply spaceId failure");
    }
}
