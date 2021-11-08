package com.csun.mall.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

public class Configuration {
    @Id
    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(hidden = true)
    private String ckey;
    @ApiModelProperty(required = false)
    private String cvalue;
    @ApiModelProperty(required = false)
    private String description;

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
     * @return key
     */
    public String getCkey() {
        return ckey;
    }

    /**
     * @param key
     */
    public void setCkey(String key) {
        this.ckey = key;
    }

    /**
     * @return value
     */
    public String getCvalue() {
        return cvalue;
    }

    /**
     * @param value
     */
    public void setCvalue(String value) {
        this.cvalue = value;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}