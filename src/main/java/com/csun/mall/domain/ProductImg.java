package com.csun.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "product_img")
public class ProductImg {
    /**
     * 主键ID
     */
    @Id
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "product_id")
    @ApiModelProperty(hidden = true)
    private Long productId;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片顺序，从小到大
     */
    private Integer sort;

    /**
     * 是否主图 是否主图，1：是，0：否
     */
    @Column(name = "is_main")
    private Integer isMain;

    /**
     * 是否启用
     */
    private Boolean enable;

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
     * @return product_id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取图片地址
     *
     * @return url - 图片地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置图片地址
     *
     * @param url 图片地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图片顺序，从小到大
     *
     * @return sort - 图片顺序，从小到大
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置图片顺序，从小到大
     *
     * @param sort 图片顺序，从小到大
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否主图 是否主图，1：是，0：否
     *
     * @return is_main - 是否主图 是否主图，1：是，0：否
     */
    public Integer getIsMain() {
        return isMain;
    }

    /**
     * 设置是否主图 是否主图，1：是，0：否
     *
     * @param isMain 是否主图 是否主图，1：是，0：否
     */
    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    /**
     * 获取是否启用
     *
     * @return enable - 是否启用
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否启用
     *
     * @param enable 是否启用
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
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