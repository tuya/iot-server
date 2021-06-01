package com.tuya.iot.suite.ability.idaas.ability;

import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyResp;

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
    Long applySpace(SpaceApplyReq spaceApplyRequest);

    Long querySpace(String spaceGroup,String spaceCode);
}
