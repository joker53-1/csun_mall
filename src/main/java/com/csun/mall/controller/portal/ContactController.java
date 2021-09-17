package com.csun.mall.controller.portal;

import com.csun.mall.domain.Contact;
import com.csun.mall.service.ContactService;
import com.csun.mall.web.response.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author Joker Zheng
 * @create 2021/9/17 14:21
 */
@RestController
@RequestMapping("contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("contactUs")
    public ResponseData<String> saveContact(String name,
                                              String email,
                                              String company,
                                              String phone,
                                              String message) {
        Contact build = Contact.builder().name(name).email(email).company(company).phone(phone).message(message).createTime(new Date()).build();
        contactService.create(build);
        return ResponseData.success("Thanks for contacting us! We will be in touch with you shortly.");
    }

    @GetMapping("contactUs")
    public ResponseData<List<Contact>> getContact(){
        return ResponseData.success(contactService.getList());
    }

}
