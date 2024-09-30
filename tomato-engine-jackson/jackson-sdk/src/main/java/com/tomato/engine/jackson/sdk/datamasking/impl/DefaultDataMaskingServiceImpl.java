package com.tomato.engine.jackson.sdk.datamasking.impl;

import com.tomato.engine.jackson.sdk.datamasking.DataMaskingService;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Slf4j
public class DefaultDataMaskingServiceImpl implements DataMaskingService {
    @Override
    public Object dataMasking(Object value, String type) {
        log.warn("数据脱敏服务暂未实现-->type-->{}",type);
        return value;
    }
}
