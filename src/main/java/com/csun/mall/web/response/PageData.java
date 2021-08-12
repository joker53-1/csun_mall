package com.ceeg.mall.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Created by Cen xr on 2021/6/24 10:19
 */
@Getter
@Setter
public class PageData<T> {

    /**
     * 总页数
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总记录数
     */
    private Long totalCount;

    /**
     * 当前页的数据
     */
    private List<T> dataList;

    public PageData() {

    }

    public PageData(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageData(Integer pageNum, Integer pageSize, Integer totalPage, Long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }

    public PageData(Integer pageNum, Integer pageSize, Integer totalPage, Long totalCount, List<T> dataList) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.dataList = dataList;
    }

//    public static <T> PageData<T> empty(UniPageReq uniPageReq) {
//        return new PageData<>(uniPageReq.getPageNum(), uniPageReq.getPageSize());
//    }

    public <R> PageData<R> map(Function<? super T, ? extends R> converter) {
        PageData<R> result = new PageData<>();
        result.setPageNum(this.pageNum);
        result.setPageSize(this.pageSize);
        result.setTotalPage(this.totalPage);
        result.setTotalCount(this.totalCount);
        if (this.dataList != null && this.dataList.size() > 0) {
            result.setDataList(this.dataList.stream().map(converter).collect(Collectors.toList()));
        }
        return result;
    }

    // todo mybatis page返回值 替换快捷转换统一
//    public static <T> PageData<T> from(Page<T> pageinfo) {
//        PageData<T> result = new PageData<>();
//        result.setTotalPage(pageinfo.getTotalPages());
//        result.setPageNum(pageinfo.getNumber() + 1);
//        result.setPageSize(pageinfo.getSize());
//        result.setTotalCount(pageinfo.getTotalElements());
//        result.setDataList(pageinfo.getContent());
//        return result;
//    }
//
//    public static <T> PageData<T> getPageData(PageRequest pageRequest, Long totalCount, List<T> dataList) {
//        PageData<T> result = new PageData<>();
//        int totalPage = Math.toIntExact((totalCount + pageRequest.getPageSize() - 1) / pageRequest.getPageSize());
//        result.setTotalPage(totalPage);
//        result.setPageNum(pageRequest.getPageNumber());
//        result.setPageSize(pageRequest.getPageSize());
//        result.setTotalCount(totalCount);
//        result.setDataList(dataList);
//        return result;
//    }


}
