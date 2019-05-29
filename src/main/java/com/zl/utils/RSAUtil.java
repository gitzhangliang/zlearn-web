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

    private static final String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9AJZR7jjzCjxSpe8rqfBDO8QjXkprTF6zThgz"
            + "xDVVlPMipj7XWKaWQTiJl/Nh532xgwtd9+P2j//+/NDcztJA8aw8Y3s0z92ImWUzDapIUBBYmdP0"
            + "D30Y2aH5dz3Tc3QPY0/DtfR9lV4RWdhQNirdDghPbMcqpv0aXZol83UiPwIDAQAB";
    private static final String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL0AllHuOPMKPFKl7yup8EM7xCNe"
            + "SmtMXrNOGDPENVWU8yKmPtdYppZBOImX82HnfbGDC1334/aP//780NzO0kDxrDxjezTP3YiZZTMN"
            + "qkhQEFiZ0/QPfRjZofl3PdNzdA9jT8O19H2VXhFZ2FA2Kt0OCE9sxyqm/RpdmiXzdSI/AgMBAAEC"
            + "gYB76CekXO3+/+XeNSTgVk/KdCM5ILbfMzkeigM55NcmXTksrRqjqV3FQcrkAbcwp0fzGTtZhotv"
            + "+KXWsD3plLmnc1Kaop/fBTaJQA8mnOXfo/58n1GQutbW09hKpgpuFejqZ9H1LJeOUNSVHXziW6/G"
            + "iIpEY36hr25UuUaM+hMzSQJBAOYtcztsFaZZI04sZJjIBlBws/nDf9XyTiD2X5sinR2ZOy3yvCgz"
            + "yAhsgb13RBTVueMiInFSCV2+zrA8+ol6nxUCQQDSNJxUnlOYVT6dXfuE6jDsN28bzlDv5MniFH6f"
            + "S4cUq6q0SScL1TNRHdTuA6hFYTUo7Zgh6ZvG+eGMtGRm2XEDAkEAlIJGktRNs6JXcRybWyfTSxss"
            + "Q72rQTKwzfun+8P9cxdmY2L1m0qtiSHZI5FLz9WFtdJUPqEbgeyWFoeBbio7cQJBALOVivGoChcc"
            + "zM+5GUbmpUFJ4rzIuNEaj3d8tuj0p8T7HG3GCXvMe3kTmXR2323WrIn44n4mjJWjqhSBkT1lC20C"
            + "QDs8t+AcKL6Kqgb9SKlkE2ULX3giuMZE5vmNWcEoKQkBLQ6lMKFI6QFG0zg8KDJPjp9IoJeUHVuO"
            + "hqE4SgxJy80=";

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

    public static void main(String[] args) {
        String s = "http%3A//127.0.0.1%3A7080/";
        System.out.println(urlDecode(s));
    }
}
