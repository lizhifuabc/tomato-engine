package com.tomato.engine.jackson.example.datamasking;

import com.tomato.engine.jackson.sdk.datamasking.DataMaskingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Slf4j
@Service
public class CustomDataMaskingServiceImpl implements DataMaskingService {
    @Override
    public Object dataMasking(Object value, String type) {
       // value = "******";
        log.info("数据脱敏服务自定义实现-->type-->{}",type);
        return value;
    }
}
