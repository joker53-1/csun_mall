package com.csun.mall.common.vo;

/**
 * @author cxr
 */

public enum RESPONSE_STATUS {
    /**
     * 成功
     */
    SUCCESS("成功"),
    Failure("失败"),
    INTERNAL_ERROR("系统错误"),
    UNAUTHORIZED_ERROR("权限不足"),
    ACCESS_DENIED_ERROR("拒绝访问"),
    BAD_CREDENTIALS_ERROR("账户或者密码错误");

    public final String value;

    RESPONSE_STATUS(String value) {
        this.value = value;
    }

}
