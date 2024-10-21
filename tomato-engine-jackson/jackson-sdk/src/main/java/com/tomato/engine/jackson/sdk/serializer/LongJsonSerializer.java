package com.tomato.engine.jackson.sdk.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Long 序列化
 *
 * @author lizhifu
 * @since 2024/9/30
 */
public class LongJsonSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null == value) {
            jsonGenerator.writeNull();
            return;
        }
        // js中最大安全整数16位 Number.MAX_SAFE_INTEGER
        String longStr = String.valueOf(value);
        if (longStr.length() > 16) {
            jsonGenerator.writeString(longStr);
        } else {
            jsonGenerator.writeNumber(value);
        }
    }
}
