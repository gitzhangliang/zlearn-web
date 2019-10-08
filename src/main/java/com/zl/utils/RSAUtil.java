package com.zl.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author tzxx
 * @date 2019/3/6.
 */
public class RSAUtil {

    public static final String KEY_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String publicKeyStr = "";
    private static final String privateKeyStr = "";

    /**
     * 获取公钥
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 获取私钥
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 签名
     * @param content
     * @return
     * @throws Exception
     */
    public static String sign(String content){
        try {
            byte[] data = content.getBytes();
            PrivateKey priK = getPrivateKey(privateKeyStr);
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initSign(priK);
            sig.update(data);
            return encryptBASE64(sig.sign());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验签
     * @param content
     * @return
     * @throws Exception
     */
    public static boolean verify(String content, String sign){
        try {
            byte[] data = content.getBytes();
            PublicKey pubK = getPublicKey(publicKeyStr);
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(pubK);
            sig.update(data);
            return sig.verify(decryptBASE64(sign));
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] decryptBASE64(String key){
        try {
            return new BASE64Decoder().decodeBuffer(key);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static String encryptBASE64(byte[] key){
        return new BASE64Encoder().encodeBuffer(key);
    }

    public static String urlEncoder(String url) {
        try {
            return URLEncoder.encode(url.replace("\n", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }
    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }
}
