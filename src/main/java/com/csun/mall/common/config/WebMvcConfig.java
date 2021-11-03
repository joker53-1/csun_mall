package com.csun.mall.common.config;

import com.csun.mall.controller.portal.interceptor.DeviceIdInterceptor;
import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //实现静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");     //映射swagger2
//                .addResourceLocations("file:/root/images/");//映射本地静态资源
    }

    @Bean
    public DeviceIdInterceptor deviceIdInterceptor(){
        return new DeviceIdInterceptor();
    }

    @Bean
    public UserTokenInterceptor userTokenInterceptor(){return new UserTokenInterceptor();}
    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deviceIdInterceptor())
                .addPathPatterns("/index/login","/");
        registry.addInterceptor(userTokenInterceptor()).addPathPatterns("/customer/shopcart/*","/admin/**","/info","/order/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
