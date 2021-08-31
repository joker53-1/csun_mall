package com.csun.mall.service;


import com.csun.mall.domain.CsrDevice;
import com.csun.mall.domain.SysDevice;
import com.csun.mall.mapper.CsrDeviceMapper;
import com.csun.mall.mapper.SysDeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class CsrDeviceService {

    @Resource
    private CsrDeviceMapper csrDeviceMapper;

    public boolean isExistDevice(String deviceId){
        Example example = new Example(CsrDevice.class);
        example.createCriteria().andEqualTo("code",deviceId);
        CsrDevice cd = csrDeviceMapper.selectOneByExample(example);
        return cd != null;
    }

    public CsrDevice apply(String deviceId, String ip, String userAgent) {
        if (deviceId != null) {
            Example example = new Example(CsrDevice.class);
            example.createCriteria().andEqualTo("code",deviceId);
            CsrDevice cd = csrDeviceMapper.selectOneByExample(example);
            if (cd != null) {
                return cd;
            }
        }
        CsrDevice device = new CsrDevice();
        device.setCode(deviceId);
        device.setCreatorIp(ip);
        device.setCreateTime(new Date());
        device.setInfo(userAgent);
        csrDeviceMapper.insert(device);
        return device;
    }
}
