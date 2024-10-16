package com.tomato.engine.web.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * log 示例 controller
 *
 * @author lizhifu
 * @since 2024/10/16
 */
@Slf4j
@RestController
public class LogExampleController {
    @GetMapping("/log")
    public String log() {
        log.info("log 示例");
        return "log 示例";
    }
}
