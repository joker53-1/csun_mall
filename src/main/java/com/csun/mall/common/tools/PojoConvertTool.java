package com.csun.mall.common.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class PojoConvertTool {

    public static <T> T convert(Object source, Class<T> target) {

        T another = null;
        if (source != null) {
            try {
                another = target.newInstance();
                BeanUtils.copyProperties(source, another);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                log.error("类转换失败", e);
            }
        }
        return another;

    }
}
