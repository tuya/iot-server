package com.tuya.iot.server.web.config.shiro;

import com.alibaba.fastjson.JSON;
import com.tuya.iot.server.web.i18n.I18nMessage;
import com.tuya.iot.server.web.util.WebUtils;
import com.tuya.iot.server.core.constant.ErrorCode;
import com.tuya.iot.server.core.constant.Response;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author benguan.zhou
 */
@Slf4j
public class LoginFilter extends AccessControlFilter {

    @Setter
    private I18nMessage i18nMessage;

    /**如果满足
     * 1 当前的subject是被认证过的。
     * 2 当前请求为登陆请求
     * 只要满足2个条件的一个即可允许操作
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        /*// 已登录或者是登录请求则允许访问
        HttpServletRequest request = (HttpServletRequest) req;
        if(log.isDebugEnabled()){
            log.debug(request.getRequestURI());
        }
        //会话中有token，说明已经被认证过
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserToken token = SessionContext.getUserToken();
            //判断session中是否有用户数据，如果有，则返回true，继续向下执行
            if (token != null) {
                ContextUtil.setUserToken(token);
                return true;
            }
        }
        // 为登陆请求
        if(isLoginRequest(req, resp)){
            //用于登录成功后跳转到登录前想访问的页面
            this.saveRequest(req);
            return true;
        }
        // 否则，进行认证
        Subject subject = getSubject(req, resp);
        return subject.isAuthenticated();

         */
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) {
        HttpServletResponse response = (HttpServletResponse) resp;
        String body = JSON.toJSONString(Response.buildFailure(ErrorCode.NO_LOGIN.getCode(),
                i18nMessage.getMessage(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg())));
        WebUtils.sendResponse(response, body);
        return false;
    }

}
