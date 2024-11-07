package com.tomato.engine.mybatis.example.domain;

import cn.mybatis.mp.db.annotations.PutValue;
import cn.mybatis.mp.db.annotations.ResultEntity;
import com.tomato.engine.mybatis.example.service.DictService;
import lombok.Data;

/**
 * example
 *
 * @author lizhifu
 * @since 2024/11/7
 */
@Data
@ResultEntity(Example.class)
public class ExampleVO {

    private String code;

    @PutValue(source = Example.class, property = "code", factory = DictService.class, method = "getValue")
    private String value;
}
