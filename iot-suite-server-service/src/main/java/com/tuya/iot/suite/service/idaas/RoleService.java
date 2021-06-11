package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.core.model.PageVO;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleService {

    Boolean createRole(String spaceId, RoleCreateReqDTO request);

    Boolean updateRole(String spaceId, String operatorUid, String roleCode, RoleUpdateReq request);

    Boolean deleteRole(String spaceId, String operatorUid, String roleCode);

    IdaasRole getRole(String spaceId, String operatorUid, String roleCode);

    List<IdaasRole> queryRolesByUser(String spaceId,String uid);

    PageVO<IdaasRole> queryRolesPagination(String spaceId, RolesPaginationQueryReq req);

    boolean deleteRoles(String permissionSpaceId, String operatorUid, Collection<String> roleCodes);

    Boolean resetRolePermissionsFromTemplate(String permissionSpaceId, String operatorUid, String roleCode);
    /**
     * 查询角色 角色旧role 检查新派权限是否包含旧权限， 移除旧权限，
     * @author mickey
     * @date 2021/6/11 14:49 [spaceId, uid, roleCodes]  java.lang.Boolean
     */
    List<String> checkAndRemoveOldRole(String spaceId, String uid, List<String> roleCodes, boolean removeOld);
    /**
     * 查询用户等级最高的角色
     * @author mickey
     * @date 2021/6/11 14:59 [spaceId, operatUserId]  com.tuya.iot.suite.service.enums.RoleTypeEnum
     */
    RoleTypeEnum userOperateRole(String spaceId, String operatUserId);
    /**
     * 查询用户等级最高的角色
     * @author mickey
     * @date 2021/6/11 14:59 [spaceId, operatUserId]  com.tuya.iot.suite.service.enums.RoleTypeEnum
     */
    RoleTypeEnum userOperateRole(String spaceId, String operatUserId, List<String> roleCodes);
}
