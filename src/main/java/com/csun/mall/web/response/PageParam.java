package com.csun.mall.web.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by Cen xr on 2021/8/14 17:12
 */
@Getter
@Setter
public class PageParam {

    private Integer pageNum = 1;
    private Integer pageSize = 2;

}
