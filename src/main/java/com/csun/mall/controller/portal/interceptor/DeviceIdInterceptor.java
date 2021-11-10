package com.csun.mall.controller.portal.interceptor;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.common.tools.RedisOperator;
import com.csun.mall.common.tools.WebTool;
import com.csun.mall.service.ConfigurationService;
import com.csun.mall.service.CsrDeviceService;
import com.csun.mall.web.response.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

public class DeviceIdInterceptor implements HandlerInterceptor {


    @Autowired
    private CsrDeviceService csrDeviceService;

    @Autowired
    private ConfigurationService configurationService;


    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();

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
//        request.setAttribute("openid","12313");
        Map<String ,String> map = configurationService.get();
        request.setAttribute("conMap",map);
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
