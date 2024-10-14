package com.tomato.engine.easy.excel.sdk.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tomato.engine.easy.excel.sdk.util.ExcelUtil;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 分批处理监听器（适用于大数据场景）
 *
 * @author lizhifu
 * @since 2024/9/30
 */
public class BatchHandleResultListener <T, R> extends AnalysisEventListener<T> {
    /**
     * 每次处理的数据量
     */
    private static int BATCH_NUMBER = 1000;

    /**
     * 临时存储读取的数据
     */
    private List<T> data;

    /**
     * rows 总行数 batchNo 当前批次数
     */
    @Getter
    private int rows, batchNo;

    /**
     * 用于校验excel模板正确性的字段
     */
    private final Field[] fields;

    /**
     * excel 类
     */
    private final Class<T> clazz;

    /**
     * 校验开关
     */
    private final boolean validateSwitch;

    /**
     * 每批数据的业务逻辑处理器，返回业务处理结果
     */
    private final Function<List<T>, R> businessHandler;

    /**
     * 用于存储每批业务处理后的返回结果
     */
    @Getter
    private final List<R> results = new ArrayList<>();


    public BatchHandleResultListener(Class<T> clazz, Function<List<T>, R> handle) {
        // 通过构造器为字段赋值，用于校验excel文件与模板是否匹配
        this(clazz, handle, BATCH_NUMBER, true);
    }

    public BatchHandleResultListener(Class<T> clazz, Function<List<T>, R> handle, int batchNumber, boolean validateSwitch) {
        // 通过构造器为字段赋值，用于校验excel文件与模板是否匹配
        this.clazz = clazz;
        this.fields = clazz.getDeclaredFields();
        // 初始化临时存储数据的集合，及外部传入的业务方法
        this.businessHandler = handle;
        BATCH_NUMBER = batchNumber;
        this.data = new ArrayList<>(BATCH_NUMBER);
        this.validateSwitch = validateSwitch;
    }

    /**
     * 每成功解析一条excel数据行时触发
     * @param row 数据
     * @param analysisContext 上下文
     */
    @Override
    public void invoke(T row, AnalysisContext analysisContext) {
        data.add(row);
        // 判断当前已解析的数据是否达到本批上限，是则执行对应的业务处理
        if (data.size() >= BATCH_NUMBER) {
            // 更新读取到的总行数、批次数
            rows += data.size();
            batchNo++;

            // 触发业务逻辑处理，获取处理结果并存储
            R result = this.businessHandler.apply(data);
            results.add(result);

            // 处理完本批数据后，使用新List替换旧List，旧List失去引用后会很快被GC
            data = new ArrayList<>(BATCH_NUMBER);
        }
    }

    /**
     * 解析完成时触发
     * @param analysisContext 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 因为最后一批可能达不到指定的上限，所以解析完之后要做一次收尾处理
        if (!data.isEmpty()) {
            R result = this.businessHandler.apply(data);
            results.add(result);

            // 更新读取到的总行数、批次数，以及清理集合辅助GC
            rows += data.size();
            batchNo++;
            data.clear();
        }
    }

    /**
     * 读取到excel头信息时触发，会将表头数据转为Map集合（用于校验导入的excel文件与模板是否匹配）
     * 当前校验逻辑不适用于多行头模板（如果是多行头的文件，请关闭表头验证）
     * 使用当前监听器的导入场景，模型类不允许出现既未忽略、又未使用ExcelProperty注解的字段；
     * @param headMap 表头数据
     * @param context 上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (validateSwitch) {
            ExcelUtil.validateExcelTemplate(headMap, clazz, fields);
        }
    }
}
