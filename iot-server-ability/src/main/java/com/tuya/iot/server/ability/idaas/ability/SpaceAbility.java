package com.tuya.iot.server.ability.idaas.ability;

import com.tuya.iot.server.ability.idaas.model.IdaasSpace;
import com.tuya.iot.server.ability.idaas.model.SpaceApplyReq;

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/05/31
 */
public interface SpaceAbility {
    /**
     * 申请权限空间
     * @param spaceApplyRequest 权限空间申请参数
     * @return 申请是否成功
     * */
    String applySpace(SpaceApplyReq spaceApplyRequest);

    IdaasSpace querySpace(String spaceGroup, String spaceCode);
}
