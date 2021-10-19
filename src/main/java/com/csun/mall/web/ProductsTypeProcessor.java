package com.csun.mall.web;

import com.csun.mall.domain.ProductCategory;
import com.csun.mall.pojo.dto.ProductCategoryWithChildren;
import com.csun.mall.pojo.dto.ShopCartDTO;
import com.csun.mall.service.CategoryService;
import com.csun.mall.service.ProductService;
import com.csun.mall.service.ShopCartService;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

public class ProductsTypeProcessor extends AbstractElementTagProcessor {
    private static final String TAG_NAME = "products-types";//标签名
    private static final int PRECEDENCE = 10000;//优先级

    public ProductsTypeProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML,// 此处理器将仅应用于HTML模式
                dialectPrefix,// 要应用于名称的匹配前缀
                TAG_NAME,// 标签名称：匹配此名称的特定标签
                true,// 将标签前缀应用于标签名称
                null,// 无属性名称：将通过标签名称匹配
                false,// 没有要应用于属性名称的前缀
                PRECEDENCE);// 优先(内部方言自己的优先)
    }

    /**
     * context 页面上下文
     * tag  标签
     */
    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag,
                             IElementTagStructureHandler structureHandler) {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(context);
        CategoryService categoryService = (CategoryService) applicationContext.getBean("categoryService");
        List<ProductCategoryWithChildren> list = categoryService.listWithChildren();
//        list.stream()
        structureHandler.setLocalVariable("productTypeList", list);
    }

}