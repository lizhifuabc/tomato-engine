package com.tomato.engine.idempotent.example.controller;

import com.tomato.engine.idempotent.sdk.annotation.Idempotent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * example
 *
 * @author lizhifu
 * @since 2024/10/15
 */
@RestController
public class ExampleController {
    @GetMapping("/hello")
    @Idempotent
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello2")
    @Idempotent(key = "#name")
    public String hello2(String name) {
        return "hello";
    }
}
