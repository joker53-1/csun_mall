package com.csun.mall.controller.sysuser;

import com.csun.mall.domain.Configuration;
import com.csun.mall.service.ConfigurationService;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author Joker Zheng
 * @create 2021/11/4 15:32
 */
@RestController
@RequestMapping("con")
public class ConfigurationController {
    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping("gete")
    public ResponseData<Map<String,String>> get(){
        return ResponseData.success(configurationService.get());
    }
}
