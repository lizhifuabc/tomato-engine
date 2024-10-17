package com.tomato.engine.jackson.sdk.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * xss json
 *
 * @author lizhifu
 * @since 2024/10/17
 */
public class XssJsonDeserializer extends JsonDeserializer<String> {
    /**
     * 反序列化方法，将 JSON 字符串解析为 Java 对象。
     *
     * @param jsonParser JSON 解析器，用于读取 JSON 数据
     * @param deserializationContext 反序列化上下文，用于处理反序列化过程中的异常和配置
     * @return 返回一个空字符串（此示例仅为示例，实际实现应根据需要返回解析后的字符串）
     * @throws IOException 如果读取 JSON 数据时发生输入输出错误
     * @throws JacksonException 如果在反序列化过程中发生 Jackson 相关的错误
     */
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return cleaning(jsonParser.getValueAsString());
    }
    /**
     * 清洗字符串
     *
     * @param value 待清洗的字符串
     * @return 清洗后的字符串
     */
    private String cleaning(String value) {
        // 如果为空，则直接返回，否则进行清洗
        if (StringUtils.hasLength(value)){
            // TODO 清理
            return value;
        }
        return value;
    }
}
