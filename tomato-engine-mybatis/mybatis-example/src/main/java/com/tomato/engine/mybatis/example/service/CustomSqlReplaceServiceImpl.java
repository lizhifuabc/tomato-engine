package com.tomato.engine.mybatis.example.service;

import com.tomato.engine.mybatis.sdk.replace.SqlReplaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sql 替换服务
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Slf4j
@Service
public class CustomSqlReplaceServiceImpl implements SqlReplaceService {
    @Override
    public String replace(String sqlId) {
        log.info("替换 sql:{}",sqlId);
//        return "SELECT t.id , t.name , t.age , t.create_time FROM example t";

        return null;
    }
}
