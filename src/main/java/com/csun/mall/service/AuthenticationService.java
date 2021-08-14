package com.csun.mall.service;

import com.csun.mall.common.enums.COMMONENUM;
import com.csun.mall.domain.SysDevice;
import com.csun.mall.domain.SysUser;
import com.csun.mall.domain.SysUserToken;
import com.csun.mall.mapper.SysUserMapper;
import com.csun.mall.mapper.SysUserTokenMapper;
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
        String tokenStr = request.getHeader(COMMONENUM.AUTHORIZATION.value);
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        Example example = new Example(SysUserToken.class);
        example.createCriteria().andEqualTo("token", tokenStr);

        return userTokenMapper.selectOneByExample(example);
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
        userTokenMapper.updateByExample(loginToken, example);
    }

    public SysUser loadUserByUsername(String username) {
        Example example = new Example(SysUser.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("username", username);
        return userMapper.selectOneByExample(example);
    }

    public SysUserToken generalUserToken(SysUser user, SysDevice device) {
        SysUserToken token = SysUserToken.builder()
                .deviceId(1L)
                .enable(true).userId(user.getId())
                .token(UUID.randomUUID().toString()).createTime(new Date()).build();
        userTokenMapper.insert(token);
        return token;
    }


}
