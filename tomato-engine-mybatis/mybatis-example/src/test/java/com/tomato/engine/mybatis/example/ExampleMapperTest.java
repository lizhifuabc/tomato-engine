package com.tomato.engine.mybatis.example;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.tenant.TenantContext;
import com.tomato.engine.mybatis.example.domain.Example;
import com.tomato.engine.mybatis.example.domain.ExampleVO;
import com.tomato.engine.mybatis.example.mapper.ExampleMapper;
import db.sql.api.impl.cmd.condition.In;
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

    @Test
    public void testSelectAll() {
        TenantContext.registerTenantGetter(() -> 1);
        List<Example> list = QueryChain.of(exampleMapper)
                .list();
        System.out.println(list);

        TenantContext.registerTenantGetter(() -> 2);
        System.out.println(TenantContext.getTenantId());
    }

    @Test
    public void putValue() {
        ThreadLocal<Integer[]> threadLocal = new ThreadLocal<>();
        threadLocal.set(new Integer[]{1, 2, 3});
        TenantContext.registerTenantGetter(threadLocal::get);


        Example example = new Example();
        example.setName("lizhifu");
        example.setAge(18);
        example.setCreateTime(LocalDateTime.now());
        example.setCode("123");
        exampleMapper.save(example);

        List<ExampleVO> list = QueryChain.of(exampleMapper)
                .returnType(ExampleVO.class)
                .list();
        System.out.println(list);
    }
}
