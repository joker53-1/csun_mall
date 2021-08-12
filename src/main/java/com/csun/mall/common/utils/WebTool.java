package com.csun.mall.common.utils;

import javax.servlet.http.HttpServletRequest;

public class WebTool {

    /**
     *
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String[] headers = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
        for (String header : headers) {
            String i = request.getHeader(header);
            if (i != null && !"".equals(i) && !"unkown".equalsIgnoreCase(i)) {
                ip = i.contains(",") ? i.split(",")[0] : i;
                break;
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     *
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }
}
