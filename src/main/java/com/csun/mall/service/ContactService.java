package com.csun.mall.service;

import com.csun.mall.domain.Contact;
import com.csun.mall.mapper.ContactMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/17 14:36
 */

@Service
@Transactional
public class ContactService {

    @Resource
    private ContactMapper contactMapper;
    public int create(Contact contact){
        return contactMapper.insert(contact);
    }

    public List<Contact> getList(){
        return contactMapper.selectAll();
    }
}
