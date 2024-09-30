package com.tomato.engine.jackson.sdk.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * BigDecimal 序列化
 *
 * @author lizhifu
 * @since 2024/9/30
 */
public class BigDecimalNullZeroSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bigDecimal == null) {
            jsonGenerator.writeNumber(BigDecimal.ZERO);
            return;
        }
        jsonGenerator.writeObject(bigDecimal);
    }
}
