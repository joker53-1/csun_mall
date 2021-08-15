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
    private int PageSize;            // 每页大小
    private long totalPage;        // 总页数
    private long totalCount;        // 总记录数
    private List<T> dataList;        // 每行显示的内容


    public static <T> PagedResult<T> from(List<T> list) {
        PageInfo<T> pageInfo = PageInfo.of(list);
        PagedResult<T> grid = new PagedResult<>();
        grid.setPageNum(pageInfo.getPageNum());
        grid.setPageSize(pageInfo.getPageSize());
        grid.setTotalPage(pageInfo.getPages());
        grid.setTotalCount(pageInfo.getTotal());
        grid.setDataList(list);
        return grid;
    }

    public static <T> PagedResult<T> from(PageInfo<T> pageInfo) {
        PagedResult<T> grid = new PagedResult<>();
        grid.setPageNum(pageInfo.getPageNum());
        grid.setPageSize(pageInfo.getPageSize());
        grid.setTotalPage(pageInfo.getPages());
        grid.setTotalCount(pageInfo.getTotal());
        grid.setDataList(pageInfo.getList());
        return grid;
    }

    public static <T, F> PagedResult<T> from(PageInfo<F> pageInfo, Class<T> targetClass) {
        PagedResult<T> grid = new PagedResult<>();
        grid.setPageNum(pageInfo.getPageNum());
        grid.setPageSize(pageInfo.getPageSize());
        grid.setTotalPage(pageInfo.getPages());
        grid.setTotalCount(pageInfo.getTotal());
        grid.setDataList(pageInfo.getList().stream().map(e -> PojoConvertTool.convert(pageInfo, targetClass)).collect(Collectors.toList()));
        return grid;
    }


}
