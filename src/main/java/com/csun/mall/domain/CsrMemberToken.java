package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "csr_member_token")
public class CsrMemberToken {
    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 是否启用（1：启用，0：冻结）
     */
    private Boolean enable;

    /**
     * 用户ID
     */
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "device_id")
    private String deviceId;

    private String token;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取是否启用（1：启用，0：冻结）
     *
     * @return enable - 是否启用（1：启用，0：冻结）
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否启用（1：启用，0：冻结）
     *
     * @param enable 是否启用（1：启用，0：冻结）
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取用户ID
     *
     * @return member_id - 用户ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置用户ID
     *
     * @param memberId 用户ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return device_id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}