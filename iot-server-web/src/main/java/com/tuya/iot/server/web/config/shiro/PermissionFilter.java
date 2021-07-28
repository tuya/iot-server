package com.tuya.iot.server.web.config.shiro;

import com.alibaba.fastjson.JSON;
import com.tuya.iot.server.web.i18n.I18nMessage;
import com.tuya.iot.server.web.util.WebUtils;
import com.tuya.iot.server.core.constant.ErrorCode;
import com.tuya.iot.server.core.constant.Response;
import com.tuya.iot.server.core.model.UserToken;
import lombok.Setter;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author benguan
 */
@Setter
public class PermissionFilter extends AuthorizationFilter {

    private I18nMessage i18nMessage;

    /**
     * 权限校验
     * 规则：例如  get /users/123 其中123为用户id
     * 则权限字符串为 get:users:*
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        if(isLoginRequest(req, resp)){
            return true;
        }
        Subject subject = this.getSubject(req, resp);
        UserToken token = (UserToken) subject.getSession().getAttribute("token");
        if(token == null){
            throw new UnauthenticatedException("Unauthenticated at isAccessAllowed");
        }
        HttpServletRequest request = (HttpServletRequest) req;
        String method = request.getMethod().toLowerCase();
        //去掉contextPath
        String relativeUri = request.getRequestURI().substring(request.getContextPath().length());
        relativeUri = StringUtils.trimLeadingCharacter(relativeUri,'/');
        relativeUri = StringUtils.trimTrailingCharacter(relativeUri,'/');
        String uriCode = String.join(":",relativeUri.split("/"));
        String permissionCode = method+":"+uriCode;
        return subject.isPermitted(permissionCode);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        Subject subject = getSubject(req, resp);
        if (subject.getPrincipal() == null) {
            String body = JSON.toJSONString(Response.buildFailure(ErrorCode.NO_LOGIN.getCode(),
                    i18nMessage.getMessage(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg())));
            WebUtils.sendResponse(response, body);
            saveRequestAndRedirectToLogin(req, resp);
        } else {
            String body = JSON.toJSONString(Response.buildFailure(ErrorCode.USER_NOT_AUTH.getCode(),
                    i18nMessage.getMessage(ErrorCode.USER_NOT_AUTH.getCode(), ErrorCode.USER_NOT_AUTH.getMsg())));
            WebUtils.sendResponse(response, body);
        }
        return false;
    }

}
