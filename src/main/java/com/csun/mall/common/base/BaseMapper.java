package com.csun.mall.common.base;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 继承自己的MyMapper
 */

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
