package com.tomato.engine.mybatis.example.mapper;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import com.tomato.engine.mybatis.example.domain.Example;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * example mapper
 *
 * @author lizhifu
 * @since 2024/10/18
 */
public interface ExampleMapper extends MybatisMapper<Example> {
}
