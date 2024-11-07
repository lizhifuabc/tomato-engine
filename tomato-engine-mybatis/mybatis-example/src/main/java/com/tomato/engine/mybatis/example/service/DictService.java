package com.tomato.engine.mybatis.example.service;

import org.springframework.stereotype.Service;

/**
 * 字典服务
 *
 * @author lizhifu
 * @since 2024/11/7
 */
@Service
public class DictService {

    /**
     * 获取值 TODO 用静态的Spring context 然后 用getBean 方法获取
     * @param code 代码
     * @return 值
     */
    public static String getValue(String code) {
        return "value" + code;
    }
}
