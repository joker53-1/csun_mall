package com.csun.mall.controller.portal.interceptor;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.common.tools.RedisOperator;
import com.csun.mall.service.CsrDeviceService;
import com.csun.mall.web.response.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class UserTokenInterceptor implements HandlerInterceptor {

    public static final String REDIS_USER_TOKEN = "mall_token_";

    @Autowired
    private CsrDeviceService csrDeviceService;

    @Autowired
    private RedisOperator redisOperator;

    public static ThreadLocal<Long> userId = new ThreadLocal<Long>();

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

        String token  = CookieTool.getCookieValue(request,"user_token");


        if(StringUtils.isNotBlank(token)){
            String uniqueToken = redisOperator.get(REDIS_USER_TOKEN+":"+token);
            if(StringUtils.isBlank(uniqueToken)){
                returnErrorResponse(response, ResponseData.failure("请登录..."));
                return false;
            }else {
                userId.set(Long.parseLong(uniqueToken));
            }
        }else{
            returnErrorResponse(response,ResponseData.failure("请登录..."));
            return false;
        }
        /**
         * false:请求被拦截，被驳回，验证出现问题
         * true：请求在经过验证校验以后，是ok的，是可以放行的
         */
        return true;
    }

    public void returnErrorResponse(HttpServletResponse response, ResponseData result){

        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(new ObjectMapper().writeValueAsString(result).getBytes("utf-8"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out!=null)
                {out.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        userId.remove();
    }
}
