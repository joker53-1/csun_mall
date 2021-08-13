package com.csun.mall;

import com.csun.mall.config.jacksonserialize.NullBeanSerializerModifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
//扫描mybatis通用mapper所在包
@MapperScan(basePackages = {"com.csun.mall.mapper"})
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

//    @Bean
//    @Primary
//    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
//                .withSerializerModifier(new NullBeanSerializerModifier()));
//        return objectMapper;
//    }
}
