package com.tomato.engine.jackson.example.controller;

import com.tomato.engine.jackson.example.resp.DataResp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@RestController
@RequestMapping("/dataMasking")
public class DataMaskingController {

    @RequestMapping("/index")
    public DataResp test() {
        DataResp dataResp = new DataResp();
        dataResp.setPhone("12345678901");
        dataResp.setEmail("EMAIL@qq.com");
        return dataResp;
    }
}
