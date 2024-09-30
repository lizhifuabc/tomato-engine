package com.tomato.engine.jackson.sdk.datamasking;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @since 2024/9/30
 */
public interface DataMaskingService {
    /**
     * 数据脱敏
     * @param value 值
     * @param type 类型
     * @return 脱敏后的值
     */
    Object dataMasking(Object value, String type);
}
