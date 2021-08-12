package com.csun.mall.web.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author cxr
 */
@Data
public class ResponseData<T> implements Serializable {

    /**
     *
     */
    private RESPONSE_STATUS status;

    /**
     *
     */
    private String msg;

    /**
     *
     */
    private T body;


    /**
     *
     */
    public ResponseData(RESPONSE_STATUS status) {
        this(status, null);
    }

    /**
     *
     */
    public ResponseData(RESPONSE_STATUS status, T body) {
        this.status = status;
        this.body = body;
    }

    /**
     *
     */
    public ResponseData(RESPONSE_STATUS status, String msg, T body) {
        this.status = status;
        this.msg = msg;
        this.body = body;
    }

    public static <T> ResponseData<T> success() {
        return new ResponseData<>(RESPONSE_STATUS.SUCCESS);
    }

    public static <T> ResponseData<T> success(T body) {
        return new ResponseData<>(RESPONSE_STATUS.SUCCESS, body);
    }

    public static <T> ResponseData<T> success(String msg, T body) {
        return new ResponseData<>(RESPONSE_STATUS.SUCCESS, msg, body);
    }

    public static <T> ResponseData<T> error(RESPONSE_STATUS status) {
        return new ResponseData<>(status);
    }

    public static <T> ResponseData<T> failure(RESPONSE_STATUS status, String msg) {
        return new ResponseData<>(status, msg, null);
    }

    public static <T> ResponseData<T> failure() {
        return new ResponseData<>(RESPONSE_STATUS.FAILURE);
    }

    public static <T> ResponseData<T> failure(String msg) {
        return new ResponseData<>(RESPONSE_STATUS.FAILURE, msg, null);
    }

    public void write(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(new ObjectMapper().writeValueAsString(this));
        response.getWriter().flush();
        response.getWriter().close();
    }
}