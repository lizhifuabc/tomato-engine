package com.tomato.engine.mybatis.example;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.tomato.engine.mybatis.example.domain.Example;
import com.tomato.engine.mybatis.example.mapper.ExampleMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * example mapper test
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@SpringBootTest
public class ExampleMapperTest {
    @Resource
    private ExampleMapper exampleMapper;

    @Test
    public void testSelect() {
        Example example = new Example();
        example.setName("lizhifu");
        example.setAge(18);
        example.setCreateTime(LocalDateTime.now());
        exampleMapper.save(example);

        List<Example> list = QueryChain.of(exampleMapper)
                .limit(10)
                .list();
        System.out.println(list);
    }
}
