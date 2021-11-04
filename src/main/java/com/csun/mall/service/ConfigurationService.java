package com.csun.mall.service;

import com.csun.mall.domain.Configuration;
import com.csun.mall.mapper.ConfigurationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Joker Zheng
 * @create 2021/11/4 15:34
 */
@Service
@Transactional
public class ConfigurationService {

    @Resource
    private ConfigurationMapper configurationMapper;

    public Map<String,String> get(){
        Map<String,String> map = new HashMap<>();
        List<Configuration> list = configurationMapper.selectAll();
        list.forEach(e->map.put(e.getCkey(),e.getCvalue()));
        return map;
    }
}
