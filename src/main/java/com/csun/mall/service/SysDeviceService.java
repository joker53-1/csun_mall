package com.csun.mall.service;


import com.csun.mall.domain.SysDevice;
import com.csun.mall.mapper.SysDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class SysDeviceService {

    @Resource
    private SysDeviceMapper sysDeviceMapper;


    public SysDevice apply(Long deviceId, String ip, String userAgent) {
        if (deviceId != null) {
            SysDevice db = sysDeviceMapper.selectByPrimaryKey(deviceId);
            if (db != null) {
                return db;
            }
        }
        SysDevice device = new SysDevice();
        device.setCreatorIp(ip);
        device.setCode(UUID.randomUUID().toString());
        device.setCreateTime(new Date());
        device.setInfo(userAgent);
        sysDeviceMapper.insert(device);
        return device;
    }
}
