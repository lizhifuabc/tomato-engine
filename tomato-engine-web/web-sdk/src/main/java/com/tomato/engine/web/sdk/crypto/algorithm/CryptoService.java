package com.tomato.engine.web.sdk.crypto.algorithm;

/**
 * 接口加密、解密
 *
 * @author lizhifu
 * @since 2024/10/18
 */
public interface CryptoService {
    /**
     * 解密字符串
     *
     * @param data 待解密的字符串
     * @return 解密后的字符串
     */
    String decrypt(String data);

    /**
     * 对字符串进行加密
     *
     * @param data 待加密的字符串
     * @return 加密后的字符串
     */
    String encrypt(String data);
}
