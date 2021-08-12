package com.csun.mall.config.provider;

import com.csun.mall.common.tools.WebTool;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class UserAuthenticationDetails extends WebAuthenticationDetails {

    /**
     *
     */
    public final Long deviceId;

    /**
     *
     */
    public final String ip;

    /**
     *
     */
    public final String userAgent;

    /**
     *
     */
    public UserAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String deviceId = request.getParameter("deviceId");
        if (deviceId == null) {
            this.deviceId = null;
        } else {
            this.deviceId = Long.parseLong(deviceId);
        }
        this.ip = WebTool.getRealIp(request);
        this.userAgent = WebTool.getUserAgent(request);
    }
}