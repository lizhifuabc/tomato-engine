package com.tomato.engine.easy.excel.sdk.oss;

import java.io.File;

/**
 * 上传文件到OSS
 *
 * @author lizhifu
 * @since 2024/9/30
 */
public interface OssService {
    /**
     * 上传文件到OSS
     * @param file 文件
     * @return 文件 url
     */
    String upload(File file);
}
