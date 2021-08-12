package com.csun.mall.service;


import com.csun.mall.mapper.SysDeviceMapper;
import com.csun.mall.domain.SysDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    private SysDeviceMapper sysDeviceMapper;


    public SysDevice apply(Long deviceId, String ip, String userAgent) {
        if (deviceId != null) {
            SysDevice db = sysDeviceMapper.selectByPrimaryKey(deviceId);
            if (db != null) {
                return db;
            }
        }
        SysDevice device = new SysDevice();
        device.setCode(UUID.randomUUID().toString());
        device.setCreateTime(new Date());
        device.setInfo(userAgent);
        sysDeviceMapper.insert(device);
        return sysDeviceMapper.selectOne(device);
    }
}
