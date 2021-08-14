package com.csun.mall.web.response;

import com.csun.mall.common.tools.PojoConvertTool;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T> {

    private int pageNum;            // 当前页数
    private int PageSize;            // 总页数
    private long records;        // 总记录数
    private List<T> rows;        // 每行显示的内容


    public static <T> PagedResult<T> from(List<T> list, PageParam pageParam) {
        PageInfo<T> pageList = PageInfo.of(list);
        PagedResult<T> grid = new PagedResult<>();
        grid.setPageNum(pageParam.getPageNum());
        grid.setRows(list);
        grid.setPageSize(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    public static <T> PagedResult<T> from(PageInfo<T> pageInfo) {
        PagedResult<T> grid = new PagedResult<>();
        grid.setPageNum(pageInfo.getPageNum());
        grid.setRows(pageInfo.getList());
        grid.setPageSize(pageInfo.getPages());
        grid.setRecords(pageInfo.getTotal());
        return grid;
    }

    public static <T, F> PagedResult<T> from(PageInfo<F> pageInfo, Class<T> targetClass) {
        PagedResult<T> grid = new PagedResult<>();
        grid.setPageNum(pageInfo.getPageNum());
        grid.setRows(pageInfo.getList().stream().map(e -> PojoConvertTool.convert(pageInfo, targetClass)).collect(Collectors.toList()));
        grid.setPageSize(pageInfo.getPages());
        grid.setRecords(pageInfo.getTotal());
        return grid;
    }


}
