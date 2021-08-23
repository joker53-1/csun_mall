package com.csun.mall.service;

import com.csun.mall.domain.CsrMember;
import com.csun.mall.domain.SysUser;
import com.csun.mall.domain.SysUserLoginLog;
import com.csun.mall.mapper.CsrMemberMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Joker Zheng
 * @create 2021/8/21 10:23
 */
@Service
public class CsrMemberService {

    @Resource
    public CsrMemberMapper csrMemberMapper;
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

    public CsrMember queryMemberById(Long id){
        return csrMemberMapper.selectByPrimaryKey(id);
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

    public void insertLoginLog(String username, String ip, String userAgent) {
        CsrMember csrMember = queryByUsername(username);
        if (csrMember == null) return;
        SysUserLoginLog loginLog = new SysUserLoginLog();
        loginLog.setUserId(sysUser.getId());
        loginLog.setCreateTime(new Date());
        loginLog.setIp(ip);
        loginLog.setUserAgent(userAgent);
        sysUserLoginLogMapper.insert(loginLog);
    }

    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param keyword
     * @param password
     * @return
     */
    public CsrMember queryUserForLogin(String keyword, String password,Integer flag){
        Example example = new Example(CsrMember.class);
        Example.Criteria userCriteria = example.createCriteria();
        if(flag==0)
            userCriteria.andEqualTo("username", keyword);
        else
            userCriteria.andEqualTo("email", keyword);
        CsrMember csrMember = csrMemberMapper.selectOneByExample(example);
        if (!new BCryptPasswordEncoder().matches(password, csrMember.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        return null;
    }
}
