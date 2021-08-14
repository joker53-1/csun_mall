package com.csun.mall.service;

import com.csun.mall.web.response.PagedGridResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pageList=new PageInfo<>(list);
        PagedGridResult grid=new PagedGridResult();
        grid.setPageNum(page);
        grid.setRows(list);
        grid.setPageSize(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

}
