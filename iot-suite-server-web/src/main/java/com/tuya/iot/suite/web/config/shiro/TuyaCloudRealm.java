package com.tuya.iot.suite.web.config.shiro;

import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.web.config.HttpRequestUtils;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Setter
public class TuyaCloudRealm extends AuthorizingRealm {
    private UserService userService;
    /**
     * TODO
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        stringSet.add("users");
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * TODO
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
        UserToken userToken = new com.tuya.iot.suite.core.model.UserToken(uid, username, session.getId(), 1);
        session.setAttribute("token", userToken);
        return new SimpleAuthenticationInfo(username, password,getName());
    }
}
