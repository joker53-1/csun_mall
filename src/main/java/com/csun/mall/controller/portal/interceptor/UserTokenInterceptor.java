package com.csun.mall.controller.portal.interceptor;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.common.tools.WebTool;
import com.csun.mall.service.CsrDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private CsrDeviceService csrDeviceService;

    /**
     * 拦截请求，在访问controller调用之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }


    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String cookie = CookieTool.getCookieValue(request,"device_id");
        if(StringUtils.isBlank(cookie)||!csrDeviceService.isExistDevice(cookie)){
            String deviceId = UUID.randomUUID().toString();
            String ip = WebTool.getRealIp(request);
            String userAgent = WebTool.getUserAgent(request);
            CookieTool.setCookie(request,response,"device_id",deviceId);
            csrDeviceService.apply(deviceId,ip,userAgent);
        }
    }

    /**
     * 请求访问controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
