package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_resource")
public class SysResource {
    @Id
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源URL
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

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
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取资源URL
     *
     * @return url - 资源URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置资源URL
     *
     * @param url 资源URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取资源分类ID
     *
     * @return category_id - 资源分类ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置资源分类ID
     *
     * @param categoryId 资源分类ID
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}