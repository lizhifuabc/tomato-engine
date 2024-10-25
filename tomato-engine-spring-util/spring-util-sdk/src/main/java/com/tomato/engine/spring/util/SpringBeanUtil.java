package com.tomato.engine.spring.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * bean 相关工具类
 *
 * @author lizhifu
 * @since 2024/10/24
 */
public class SpringBeanUtil {
    /**
     * 复制bean的属性
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }


    /**
     * 复制对象属性值到另一个对象
     *
     * @param source 源对象
     * @param target 目标对象的Class类型
     * @param <T>    目标对象的类型
     * @return 目标对象，如果源对象或目标对象Class为null，则返回null
     * @throws RuntimeException 如果复制过程中发生异常
     */
    public static <T> T copy(Object source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        try {
            Constructor<T> constructor = target.getDeclaredConstructor();
            // 如果构造函数是私有的，可以设置可访问
            constructor.setAccessible(true);
            T newInstance = constructor.newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将一个源列表中的元素复制到一个新列表中，并将复制后的元素类型转换为指定的类型。
     *
     * @param source 源列表，包含要复制的元素
     * @param target 目标类型，指定复制后元素的新类型
     * @param <T>    源列表中元素的类型
     * @param <K>    目标列表中元素的类型
     * @return 一个包含转换后元素的新列表
     */
    public static <T, K> List<K> copyList(List<T> source, Class<K> target) {
        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(e -> copy(e, target)).collect(Collectors.toList());
    }
}
