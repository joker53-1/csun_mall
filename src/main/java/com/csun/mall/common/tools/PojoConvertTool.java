package com.csun.mall.common.tools;

import com.csun.mall.domain.CsrMember;
import com.csun.mall.pojo.dto.CsrMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class PojoConvertTool {

    public static <T> T convert(Object source, Class<T> target) {

        T another = null;
        if (source != null) {
            try {
                another = target.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, another);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                log.error("类转换失败", e);
            }
        }
        return another;
    }

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(str.contains("123"));
    }
}
