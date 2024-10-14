package com.tomato.engine.easy.excel.sdk.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tomato.engine.easy.excel.sdk.util.ExcelUtil;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用监听器
 *
 * @author lizhifu
 * @since 2024/9/30
 */
public class CommonListener <T> extends AnalysisEventListener<T> {

    /**
     * 数据
     */
    @Getter
    private final List<T> data;

    /**
     * 字段
     */
    private final Field[] fields;

    /**
     * excel 类
     */
    private final Class<T> clazz;

    /**
     * 校验开关
     */
    private boolean validateSwitch = true;

    /**
     * 构造函数
     *
     * @param clazz 类
     * @param validateSwitch 校验开关
     */
    public CommonListener(Class<T> clazz, boolean validateSwitch) {
        fields = clazz.getDeclaredFields();
        this.clazz = clazz;
        this.data = new ArrayList<T>();
        this.validateSwitch = validateSwitch;
    }

    /**
     * 构造函数
     *
     * @param clazz 类
     */
    public CommonListener(Class<T> clazz) {
        fields = clazz.getDeclaredFields();
        this.clazz = clazz;
        this.data = new ArrayList<T>();
    }

    /**
     * 每解析到一行数据都会触发
     * @param t 数据
     * @param analysisContext 上下文
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        data.add(t);
    }

    /**
     * 读取到excel头信息时触发，会将表头数据转为Map集合
     * @param headMap 头信息
     * @param context 上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (validateSwitch) {
            ExcelUtil.validateExcelTemplate(headMap, clazz, fields);
        }
    }

    /**
     * 所有数据解析完之后触发
     * @param analysisContext 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
