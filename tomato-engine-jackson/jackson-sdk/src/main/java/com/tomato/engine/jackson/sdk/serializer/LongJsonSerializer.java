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

    public static final LongJsonSerializer INSTANCE = new LongJsonSerializer();


    /**
     * 将给定的 Long 值序列化为 JSON 格式。
     *
     * @param value 需要序列化的 Long 值
     * @param jsonGenerator JSON 生成器，用于将序列化的数据写入 JSON 流
     * @param serializerProvider 序列化提供器，用于获取序列化过程中的各种信息
     * @throws IOException 如果在序列化过程中发生输入输出错误，将抛出 IOException 异常
     */
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
