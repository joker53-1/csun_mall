package com.csun.mall.controller.sysuser;

import com.csun.mall.domain.Configuration;
import com.csun.mall.service.ConfigurationService;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author Joker Zheng
 * @create 2021/11/4 15:32
 */
@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {
    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("get")
    public ResponseData<List<Configuration>> get(){
        return ResponseData.success(configurationService.getAll());
    }

    @PostMapping("update")
    public ResponseData update(Configuration configuration){
        int res = configurationService.update(configuration);
        if(res>0)
            return ResponseData.success();
        return ResponseData.failure();
    }

    @PutMapping("add")
    public ResponseData add(@NotNull String key, @NotNull String value, String des){
        int res = configurationService.add(key,value,des);
        if(res>0)
            return ResponseData.success();
        return ResponseData.failure();
    }

}
