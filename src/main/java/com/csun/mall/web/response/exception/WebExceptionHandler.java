package com.csun.mall.web.response.exception;



import com.csun.mall.web.response.RESPONSE_STATUS;
import com.csun.mall.web.response.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class WebExceptionHandler {

    /**
     *
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData exceptionHandler(Exception e) {
        LogManager.getLogger().error(e);
        return ResponseData.error(RESPONSE_STATUS.INTERNAL_ERROR);
    }
}