package com.csun.mall.pojo.dto.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductsVO {

    private Long id;

    private String name;

    private String typeCode;

    private String typeName;

    private String pic;

    private String description;

    private String descriptionPic;

    private Date createTime;

}
