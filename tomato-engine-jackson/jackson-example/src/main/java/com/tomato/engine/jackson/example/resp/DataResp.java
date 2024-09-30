package com.tomato.engine.jackson.example.resp;

import com.tomato.engine.jackson.sdk.annotation.DataMasking;
import lombok.Data;

/**
 * 数据脱敏返回
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Data
public class DataResp {

    @DataMasking(type = "phone")
    private String phone;

    private String email;
}
