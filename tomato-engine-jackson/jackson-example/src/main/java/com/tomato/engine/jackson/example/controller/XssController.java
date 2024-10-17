package com.tomato.engine.jackson.example.controller;

import com.tomato.engine.jackson.sdk.annotation.DataMasking;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * xss 测试
 *
 * @author lizhifu
 * @since 2024/10/17
 */
@RestController
public class XssController {
    @GetMapping("/xss")
    public TestRequest xss(@RequestBody TestRequest request) {
        return request;
    }

    @Setter
    @Getter
    public static class TestRequest {
        private String name;

        @DataMasking(type = "phone")
        private String phone;
    }
}
