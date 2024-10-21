package com.tomato.engine.jackson.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LongJsonSerializer
 *
 * @author lizhifu
 * @since 2024/10/21
 */
@RestController
public class LongJsonSerializerController {
    @GetMapping("/long")
    public Long test() {
        return 12345678901234567L;
    }
}
