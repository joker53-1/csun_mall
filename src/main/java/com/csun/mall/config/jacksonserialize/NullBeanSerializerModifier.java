package com.csun.mall.config.jacksonserialize;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;

public class NullBeanSerializerModifier extends BeanSerializerModifier {

    private static final JsonSerializer NULL_ARRAY_JSON_SERIALIZER = new NullArrayJsonSerializer();
    private static final JsonSerializer NULL_STRING_JSON_SERIALIZER = new NullStringJsonSerializer();
    private static final JsonSerializer NULL_OBJECT_JSON_SERIALIZER = new NullObjectJsonSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List beanProperties) {
        for (Object beanProperty : beanProperties) {
            BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
            JavaType javaType = writer.getType();

            if (javaType.isCollectionLikeType()) {
                //集合
                writer.assignNullSerializer(NullBeanSerializerModifier.NULL_ARRAY_JSON_SERIALIZER);
            } else if (javaType.isEnumType() || javaType.getRawClass() == String.class) {
                //String或enum
                writer.assignNullSerializer(NullBeanSerializerModifier.NULL_STRING_JSON_SERIALIZER);
            } else if (javaType.isJavaLangObject()) {
                //其他object
                writer.assignNullSerializer(NullBeanSerializerModifier.NULL_OBJECT_JSON_SERIALIZER);
            }
        }
        return beanProperties;
    }

}
