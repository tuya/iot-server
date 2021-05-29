package com.tuya.iot.suite.web.config.shiro;

import com.alibaba.fastjson.JSON;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.util.WebUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PermissionFilter extends AuthorizationFilter {

    //private UriPermService uriPermService;

    private RedisCache dataCache;

    private I18nMessage i18nMessage;

    public void setI18nMessage(I18nMessage i18nMessage) {
        this.i18nMessage = i18nMessage;
    }

    /*public void setUriPermService(UriPermService uriPermService) {
        this.uriPermService = uriPermService;
    }*/

    /*private Map<String, Set<String>> getUriPermMap() {
    	return uriPermService.query();
        // 获取uri和权限的映射
       *//* Map<String, Set<String>> cachedMap = (Map<String, Set<String>>) dataCache.get("uriPermMap");
        if (cachedMap == null) {
            Map<String, Set<String>> uriPermMap = uriPermService.query();
            dataCache.put("uriPermMap", uriPermMap);
            return uriPermMap;
        }
        return cachedMap;*//*
    }*/

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        if(isLoginRequest(req, resp)){
            return true;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        Subject subject = this.getSubject(req, resp);
        //TODO
        return subject.isPermittedAll();
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
            /*if (WebUtils.isAjax(request)) {
                R r = R.error(ReturnCode.UNAUTHORED, ReturnCode.UNAUTHORED_MSG);
                response.getWriter().print(JSONObject.toJSON(r));
            } else {
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.sendRedirect(request, response, unauthorizedUrl);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }*/
        }
        return false;
    }

    public void setDataCache(RedisCache dataCache) {
        this.dataCache = dataCache;
    }

}
