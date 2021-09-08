package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table
public class Products {
    private Long id;

    private Integer sort;

    @Column(name = "create_time")
    private Date createTime;

    private String name;

    @Column(name = "type_code")
    private String typeCode;

    private String pic;

    private String description;

    @Column(name = "description_pic")
    private String descriptionPic;

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
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return type_code
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic
     */
    public void setPic(String pic) {
        this.pic = pic;
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

    /**
     * @return description_pic
     */
    public String getDescriptionPic() {
        return descriptionPic;
    }

    /**
     * @param descriptionPic
     */
    public void setDescriptionPic(String descriptionPic) {
        this.descriptionPic = descriptionPic;
    }
}