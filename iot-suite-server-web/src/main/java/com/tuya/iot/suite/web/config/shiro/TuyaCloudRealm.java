package com.tuya.iot.suite.web.config.shiro;

import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.service.idaas.PermissionCheckService;
import com.tuya.iot.suite.service.idaas.PermissionService;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.web.config.HttpRequestUtils;
import com.tuya.iot.suite.web.config.ProjectProperties;
import com.tuya.iot.suite.web.util.SessionContext;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Component
public class TuyaCloudRealm extends AuthorizingRealm {
    @Autowired
    private ProjectProperties projectProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionCheckService permissionCheckService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    /**
     * 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserToken userToken = SessionContext.getUserToken();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<IdaasRole> roles = roleService.queryRolesByUser(projectProperties.getPermissionSpaceId(),userToken.getUserId());
        info.addRoles(roles.stream().map(it->it.getRoleCode()).collect(Collectors.toSet()));

        List<IdaasPermission> perms = permissionService.queryPermissionsByUser(projectProperties.getPermissionSpaceId(),userToken.getUserId());

        info.addStringPermissions(perms.stream().map(it->it.getPermissionCode()).collect(Collectors.toSet()));
        return info;
    }

    /**
     * 认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        String uid = userService.login(username,password).getUid();
        HttpSession session = HttpRequestUtils.getHttpSession();
        //小心，有两个类叫UserToken，在不同包下面，不要搞混了
        SessionContext.setUserToken(new UserToken(uid, username, session.getId(), 1));
        return new SimpleAuthenticationInfo(username, password,getName());
    }
}
