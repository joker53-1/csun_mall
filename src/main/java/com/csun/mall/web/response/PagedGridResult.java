package com.csun.mall.web.response;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PagedGridResult {

    private int pageNum;			// 当前页数
    private int PageSize;			// 总页数
    private long records;		// 总记录数
    private List<?> rows;		// 每行显示的内容

    public static PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pageList=new PageInfo<>(list);
        PagedGridResult grid=new PagedGridResult();
        grid.setPageNum(page);
        grid.setRows(list);
        grid.setPageSize(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }


}
