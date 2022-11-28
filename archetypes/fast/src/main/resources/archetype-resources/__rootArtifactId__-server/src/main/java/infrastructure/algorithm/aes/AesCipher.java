#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.algorithm.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import ${package}.infrastructure.algorithm.AlgorithmException;

/**
 * AES 加解密
 *
 * @author dfenghuang
 * @date 2022/11/2 09:57
 */
public class AesCipher {

    public static String KEY_ALGORITHM = "AES";
    private String algorithm;
    private SecretKeySpec keySpec;
    private IvParameterSpec ivParameterSpec;

    /**
     * 默认算法 {@link AesCipher${symbol_pound}AesCipher(String, byte[], byte[])}
     *
     * @param key
     * @param iv
     * @see AesAlgorithm${symbol_pound}AES_CBC_PKCS5PADDING
     */
    public AesCipher(byte[] key, byte[] iv) {
        this(AesAlgorithm.AES_CBC_PKCS5PADDING, key, iv);
    }

    /**
     * 指定算法
     *
     * @param algorithm 算法 {@link AesAlgorithm}
     * @param key 密钥不能为空
     * @param iv ECB算法可以空
     */
    public AesCipher(String algorithm, byte[] key, byte[] iv) {
        if (StringUtils.isEmpty(algorithm)) {
            throw new IllegalArgumentException("algorithm must not empty");
        }
        if (ArrayUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key must not empty");
        }
        this.ivParameterSpec = ArrayUtils.isEmpty(iv) ? null : new IvParameterSpec(iv);
        this.algorithm = algorithm;
        this.keySpec = new SecretKeySpec(key, KEY_ALGORITHM);;
    }

    /**
     * 加密
     *
     * @param content 不能为null或者空
     * @return
     */
    public byte[] encrypt(byte[] content) {
        return this.doFinal(Cipher.ENCRYPT_MODE, content);
    }

    /**
     * 解密
     *
     * @param content 不能为null或者空
     * @return
     */
    public byte[] decrypt(byte[] content) {
        return this.doFinal(Cipher.DECRYPT_MODE, content);
    }

    private byte[] doFinal(int mode, byte[] content) {
        if (ArrayUtils.isEmpty(content)) {
            throw new IllegalArgumentException("content must not empty");
        }

        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            if (null != this.ivParameterSpec) {
                cipher.init(mode, this.keySpec, this.ivParameterSpec);
            } else {
                cipher.init(mode, this.keySpec);
            }
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            throw new AlgorithmException("ase error", e);
        }
    }
}
