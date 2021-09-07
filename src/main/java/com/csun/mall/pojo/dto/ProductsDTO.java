package com.csun.mall.pojo.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    private Long id;

    private String name;

    private Integer sort;

    private Long categoryId;

    private String categoryName;

    private List<String> pic;

    private String description;

    private String descriptionPic;

    private Date createTime;

}
