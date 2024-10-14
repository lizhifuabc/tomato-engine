package com.tomato.engine.easy.excel.sdk.oss.impl;

import com.tomato.engine.easy.excel.sdk.oss.OssService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 上传文件到OSS
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Slf4j
public class DefaultOssServiceImpl implements OssService {
    @Override
    public String upload(File file) {
        throw new RuntimeException("请在实现类中实现该方法");
    }
}
