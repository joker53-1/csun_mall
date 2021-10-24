package com.csun.mall.controller.sysuser;

import com.csun.mall.pojo.dto.MessageRecoreDTO;
import com.csun.mall.service.MessageRecordService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Joker Zheng
 * @create 2021/9/9 14:59
 */

@RestController
@RequestMapping("/sysuser/message")
public class MessageController {


    @Autowired
    private MessageRecordService messageRecordService;

    @GetMapping("page")
    public ResponseData<PageResult<MessageRecoreDTO>> page(Long messageId, PageParam param) {
//        param.setPageSize(10);
        PageResult<MessageRecoreDTO> page = messageRecordService.page(messageId, param);

        return ResponseData.success(page);
    }

}
