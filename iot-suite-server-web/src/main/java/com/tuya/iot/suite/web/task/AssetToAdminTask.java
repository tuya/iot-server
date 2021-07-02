package com.tuya.iot.suite.web.task;

import com.tuya.iot.suite.service.asset.AssetService;
import com.tuya.iot.suite.web.config.ProjectProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author mickey
 * @date 2021年06月14日 17:55
 */
@Slf4j
@EnableScheduling
@Component
public class AssetToAdminTask {

    @Autowired
    private AssetService assetService;
    @Autowired
    private ProjectProperties projectProperties;

    /**
     * 系统管理员授权临时方案
     * @author mickey
     * @date 2021/6/14 18:19
     */
    @Scheduled(cron = "0 */30  * * * ?  ")
    public void grantAssetToAllAdmin() {
        try {
            Boolean result = assetService.grantAllAssetToAdmin(projectProperties.getPermissionSpaceId());
            log.info("给超管授权结果：{}", result);
        } catch (Exception e) {
            log.info("给超管授权失败！", e);
        }
    }
}

