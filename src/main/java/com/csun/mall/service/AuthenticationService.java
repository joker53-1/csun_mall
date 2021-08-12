package com.csun.mall.service;

import com.csun.mall.mapper.SysUserMapper;
import com.csun.mall.mapper.SysUserTokenMapper;
import com.csun.mall.pojo.SysDevice;
import com.csun.mall.pojo.SysUser;
import com.csun.mall.pojo.SysUserToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @author cxr
 */
@Service
public class AuthenticationService {

    @Autowired
    private SysUserTokenMapper userTokenMapper;

    @Autowired
    private SysUserMapper userMapper;


    public SysUserToken getTokenByRequest(HttpServletRequest request) {
        String tokenStr = request.getHeader("Authorization");
        if (StringUtils.isEmpty(tokenStr)) {
            return null;
        }
        return userTokenMapper.selectByPrimaryKey(tokenStr);
    }

    public SysUserToken getLoginToken() {
        return this.getLoginToken(SecurityContextHolder.getContext().getAuthentication());
    }

    public SysUserToken getLoginToken(Authentication authentication) {
        return (SysUserToken) authentication.getPrincipal();
    }

    public void revokeUserToken(SysUserToken loginToken) {
        loginToken.setEnable(false);
        Example example = new Example(SysUserToken.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("id", loginToken.getId());
        userTokenMapper.updateByExample(loginToken,example);
    }

    public SysUser loadUserByUsername(String username) {
        Example example = new Example(SysUser.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("username", username);
        return userMapper.selectOneByExample(example);
    }

    public SysUserToken generalUserToken(SysUser user, SysDevice device) {
        SysUserToken token = SysUserToken.builder()
                .deviceId(device.getId())
                .enable(true).userId(user.getId())
                .token(UUID.randomUUID().toString()).createTime(new Date()).build();
        userTokenMapper.insert(token);
        return token;
    }


}
