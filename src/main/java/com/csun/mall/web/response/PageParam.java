//package com.ceeg.mall.web.response;
//
//import lombok.Data;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
///**
// * @author Created by Cen xr on 2021/6/24 10:27
// */
// todo 分页插件分装
//@Data
//public class PageParam {
//    private int pageNum = 1;
//    private int pageSize = 20;
//
//    public PageParam() {
//
//    }
//
//    public PageParam(int pageNum, int pageSize) {
//        if (pageNum < 1) {
//            pageNum = 1;
//        }
//        if (pageSize < 1) {
//            pageSize = 20;
//        }
//        this.pageNum = pageNum;
//        this.pageSize = pageSize;
//    }
//
//    public PageRequest pageRequest() {
//        return PageRequest.of(pageNum - 1, pageSize);
//    }
//
//    public PageRequest pageRequest(Sort sort) {
//        return PageRequest.of(pageNum - 1, pageSize, sort);
//    }
//
//
//}
