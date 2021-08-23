package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "csr_device")
public class CsrDevice {
    @Id
    private Long id;

    private String code;

    private String info;

    private String token;

    @Column(name = "creator_ip")
    private String creatorIp;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_sync_ip")
    private String lastSyncIp;

    @Column(name = "last_sync_time")
    private Date lastSyncTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
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
     * @return creator_ip
     */
    public String getCreatorIp() {
        return creatorIp;
    }

    /**
     * @param creatorIp
     */
    public void setCreatorIp(String creatorIp) {
        this.creatorIp = creatorIp;
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

    /**
     * @return last_sync_ip
     */
    public String getLastSyncIp() {
        return lastSyncIp;
    }

    /**
     * @param lastSyncIp
     */
    public void setLastSyncIp(String lastSyncIp) {
        this.lastSyncIp = lastSyncIp;
    }

    /**
     * @return last_sync_time
     */
    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    /**
     * @param lastSyncTime
     */
    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
}