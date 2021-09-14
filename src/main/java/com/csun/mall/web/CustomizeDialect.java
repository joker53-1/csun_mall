package com.csun.mall.web;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomizeDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "cxr dialect";//定义方言名称

    private static final String DIALECT_NAME_PREFIX = "";

    public CustomizeDialect() {
        super(DIALECT_NAME, DIALECT_NAME_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<>();
//        processors.add(new NewsProcessor(dialectPrefix));//添加我们定义的标签
//        processors.add(new ProductsProcessor(dialectPrefix));//添加我们定义的标签
        processors.add(new ProductsTypeProcessor(dialectPrefix));//添加我们定义的标签
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}
