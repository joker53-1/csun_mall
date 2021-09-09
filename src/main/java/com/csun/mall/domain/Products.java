package com.csun.mall.domain;

import lombok.Data;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table
public class Products implements IDynamicTableName , Serializable {

    @Id
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


    @Override
    public String getDynamicTableName() {
        return "products_en";
    }
}