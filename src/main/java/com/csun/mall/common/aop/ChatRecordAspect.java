package com.csun.mall.common.aop;

import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.domain.MessageRecord;
import com.csun.mall.pojo.dto.MessageVO;
import com.csun.mall.service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 聊天记录切面类
 *
 * @author yanpanyi
 * @date 2019/4/23
 */
@Aspect
@Component
@Slf4j
public class ChatRecordAspect {

    @Resource
    private MessageRecordService chatRecordService;

    @Pointcut("@annotation(com.csun.mall.common.annotation.ChatRecord)")
    public void chatRecordPointcut() {
    }

    @Before("chatRecordPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        log.debug("before -> {}", joinPoint);

        MessageVO messageVO = null;
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            if (obj instanceof MessageVO) {
                messageVO = (MessageVO) obj;
                messageVO.setSendTime(new Date());
                break;
            }
        }

        Assert.notNull(messageVO, "方法必需以MessageVO类或该类的子类作为参数");


        log.debug("添加聊天记录 -> {}", messageVO);
        MessageRecord message = PojoConvertTool.convert(messageVO, MessageRecord.class);
        chatRecordService.addRecord(message);
    }

}
