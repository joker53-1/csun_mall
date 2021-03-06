package com.csun.mall.service;

import com.csun.mall.domain.*;
import com.csun.mall.mapper.CsrMemberLoginLogMapper;
import com.csun.mall.mapper.CsrMemberMapper;
import com.csun.mall.mapper.CsrMemberTokenMapper;
import com.csun.mall.web.response.ResponseData;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @Author Joker Zheng
 * @create 2021/8/21 10:23
 */
@Service
public class CsrMemberService {

    @Resource
    public CsrMemberMapper csrMemberMapper;

    @Resource
    public CsrMemberTokenMapper csrMemberTokenMapper;

    @Resource
    public CsrMemberLoginLogMapper csrMemberLoginLogMapper;
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public CsrMember queryByUsername(String username){
        Example userExample = new Example(CsrMember.class);
        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username", username);

        CsrMember result = csrMemberMapper.selectOneByExample(userExample);

        return result;
    }
    public CsrMember queryMemberByEmail(String email){
        Example userExample = new Example(CsrMember.class);
        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("email", email);

        CsrMember result = csrMemberMapper.selectOneByExample(userExample);

        return result;
    }
    public CsrMember queryMemberById(Long id){
        CsrMember member = csrMemberMapper.selectByPrimaryKey(id);
        member.setPassword(null);
        return member;
    }

    /**
     * 创建用户
     * @return
     */
    public CsrMember createUser(String username, String email,String password){
        CsrMember csrMember = new CsrMember();
        csrMember.setUsername(username);
        csrMember.setEnable(1);
        csrMember.setEmail(email);
        String encodePassword = new BCryptPasswordEncoder().encode(password);
        csrMember.setPassword(encodePassword);
        csrMember.setCreateTime(new Date());
        csrMember.setUpdateTime(new Date());
        csrMemberMapper.insert(csrMember);
        return csrMember;
    }

    public CsrMemberToken getToken(String token){
        Example example = new Example(CsrMemberToken.class);
        example.createCriteria().andEqualTo("token",token);
        return csrMemberTokenMapper.selectOneByExample(example);
    }

    public void insertLoginLog(Long id, String ip, String userAgent) {
        CsrMemberLoginLog loginLog = new CsrMemberLoginLog();
        loginLog.setMemberId(id);
        loginLog.setCreateTime(new Date());
        loginLog.setIp(ip);
        loginLog.setUserAgent(userAgent);
        csrMemberLoginLogMapper.insert(loginLog);
    }

    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param keyword
     * @return
     */
    public CsrMember queryUserForLogin(String keyword,String password){
        Example example = new Example(CsrMember.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.orEqualTo("username", keyword);
        userCriteria.orEqualTo("email", keyword);
        CsrMember csrMember = csrMemberMapper.selectOneByExample(example);
        if(csrMember!=null) {
            if (!new BCryptPasswordEncoder().matches(password, csrMember.getPassword())) {
                return null;
            }
            csrMember.setPassword(null);
        }
        return csrMember;
    }

    public CsrMemberToken generalToken(CsrMember csrMember, String deviceId) {
        CsrMemberToken csrMemberToken = new CsrMemberToken();
        csrMemberToken.setEnable(true);
        csrMemberToken.setToken(UUID.randomUUID().toString());
        //TODO
        csrMemberToken.setDeviceId(deviceId);
        csrMemberToken.setCreateTime(new Date());
        csrMemberToken.setMemberId(csrMember.getId());
        csrMemberTokenMapper.insert(csrMemberToken);
        return csrMemberToken;
    }

    public void revokeUserToken(CsrMemberToken loginToken) {
        loginToken.setEnable(false);
        Example example = new Example(SysUserToken.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("id", loginToken.getId());
        csrMemberTokenMapper.updateByExample(loginToken, example);
    }

    public CsrMember updateInfo(Long id,String nickname, String phone, Integer gender, String city){
        CsrMember newMember = new CsrMember();
        newMember.setId(id);
        newMember.setCity(city);
        newMember.setPhone(phone);
        newMember.setGender(gender);
        newMember.setNickname(nickname);
        newMember.setUpdateTime(new Date());
        if(csrMemberMapper.updateByPrimaryKeySelective(newMember)>=0){
            return queryMemberById(id);
        }
        return null;
    }

    public int change(Long id,String oldPassword,String password){
        CsrMember csrMember = csrMemberMapper.selectByPrimaryKey(id);
        if(csrMember!=null) {
            if (!new BCryptPasswordEncoder().matches(oldPassword, csrMember.getPassword())) {
                return 0;
            }
            else {
                CsrMember newMember = new CsrMember();
                newMember.setId(id);
                newMember.setPassword(new BCryptPasswordEncoder().encode(password));
                newMember.setUpdateTime(new Date());
                return csrMemberMapper.updateByPrimaryKeySelective(newMember);
            }
        }
        return -1;
    }

}
