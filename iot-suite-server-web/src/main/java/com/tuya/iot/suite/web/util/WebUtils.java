package com.tuya.iot.suite.web.util;

import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author benguan.zhou
 */
public class WebUtils {

    public static final String PATH_SEPARATOR = "/";

    @SneakyThrows
    public static void sendResponse(HttpServletResponse response, String content) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.write(content);
        out.flush();
        out.close();
    }

    /**
     * 判断请求是否是Ajax请求
     *
     * @param request
     * @return
     * @author wzf
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
    }

    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 如果是ajax请求，则需要前端配合，判断自定义响应头是否包含X-Redirect-Page
     */
    public static void sendRedirect(HttpServletRequest request, HttpServletResponse resp, String url) throws IOException {
        url = addJsessionid(request, url);
        if (isAjax(request)) {
            resp.addHeader("X-Redirect-Url", url);
        } else {
            resp.sendRedirect(url);
        }
    }

    public static void sendRedirect(HttpServletRequest request, HttpServletResponse resp, String url, String sessionid) throws IOException {
        url = addJsessionid(request, url, sessionid);
        if (isAjax(request)) {
            resp.addHeader("X-Redirect-Url", url);
        } else {
            resp.sendRedirect(url);
        }
    }

    private static String addJsessionid(HttpServletRequest request, String url, String sessionid) {
        //如果是重定向到当前域
        if (url.startsWith(PATH_SEPARATOR)) {
            //String sessionId = getUriPathSegmentParamValue(request.getRequestURI(),"JSESSIONID");
            if (sessionid != null) {
                int index = url.indexOf("?");
                if (index > 0) {
                    url = url.substring(0, index) + ";JSESSIONID=" + sessionid + url.substring(index);
                } else {
                    url = url + ";JSESSIONID=" + sessionid;
                }
            }
        }
        return url;
    }

    public static void sendAjaxRedirect(HttpServletRequest request, HttpServletResponse resp, String url) {
        url = addJsessionid(request, url);
        resp.addHeader("X-Redirect-Url", url);
    }

    private static String addJsessionid(HttpServletRequest request, String url) {
        String sid = SecurityUtils.getSubject().getSession(true).getId().toString();
        return addJsessionid(request, url, sid);
    }

    private static String getUriPathSegmentParamValue(String uri, String paramName) {
        int queryStartIndex = uri.indexOf('?');
        //get rid of the query string
        if (queryStartIndex >= 0) {
            uri = uri.substring(0, queryStartIndex);
        }

        //now check for path segment parameters:
        int paramSegmentIndex = uri.indexOf(';');
        if (paramSegmentIndex < 0) {
            //no path segment params - return:
            return null;
        }

        //there are path segment params, let's get the last one that may exist:

        final String TOKEN = paramName + "=";

        //uri now contains only the path segment params
        String paramSegment = uri.substring(paramSegmentIndex + 1);

        //we only care about the last JSESSIONID param:
        int tokenIndex = paramSegment.lastIndexOf(TOKEN);
        if (tokenIndex < 0) {
            //no segment param:
            return null;
        }
        paramSegment = paramSegment.substring(tokenIndex + TOKEN.length());
        //去掉片段中的其他参数
        int index = paramSegment.indexOf(';');
        if (index >= 0) {
            paramSegment = paramSegment.substring(0, index);
        }
        //what remains is the value
        return paramSegment;
    }
}
