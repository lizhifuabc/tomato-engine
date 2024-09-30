package com.tomato.engine.jackson.sdk.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.tomato.engine.jackson.sdk.annotation.DataMasking;
import com.tomato.engine.jackson.sdk.datamasking.DataMaskingService;
import jakarta.annotation.Resource;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * 数据脱敏 序列化
 *
 * @author lizhifu
 * @since 2024/9/29
 */
public class DataMaskingSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    @Resource
    private DataMaskingService dataMaskingService;

    /**
     * 脱敏类型
     */
    private String type;

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (ObjectUtils.isEmpty(value)) {
            jsonGenerator.writeObject(value);
            return;
        }

        jsonGenerator.writeObject(dataMaskingService.dataMasking(value, type));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty property) throws JsonMappingException {
        // 判断beanProperty是不是空
        if (null == property) {
            return serializerProvider.findNullValueSerializer(null);
        }

        DataMasking annotation = property.getAnnotation(DataMasking.class);
        if (null == annotation) {
            return serializerProvider.findValueSerializer(property.getType(), property);
        }

        type = annotation.type();
        return this;
    }
}
