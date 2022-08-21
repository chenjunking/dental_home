package com.dental.home.app.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * AES+RSA组合加密
 * 客户端使用随机产生的16位AES的密钥对参数进行AES加密,通过使用RSA公钥对AES密钥进行公钥加密.
 * 服务端对加密后的AES密钥进行RSA私钥解密,拿到密钥原文,对加密后的参数进行AES解密,拿到原始内容.
 */
@Slf4j
public class SecretUtil {
    public static final String RSAPublicKeyStrA = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoXut81Hg6q5vlUHmhP0dWFLfCKTAkmIZNhfOW7v79U74FtsuCjyNL9/DQGxEpW2RUP5gWDMHntksdyzwLZEAqV1P9i5m8wYFtcmg9BhkHcvGYSoHX8+2C3Tabq5Ul4+b+sWK6y2h6o+0UGgWdvu0Qh08LA1wNsL4FxCtAabfxi+0Sfp6MOj8ej/qVnIgA2+h/AAzZzRBTu7ems0WY2j08++CiqYQCRsKkRhx+zOP4KTHGhME5EXH+JzHVo81NN+HLIZvfoBEVFNG0QMsLn/NZi44P2tZ4EFFWf+BfbiYwKiU+QWDcTDVzzNloau/RAunyOt0Ia+hfpVT4VzCfNgnHypMx4DCEYCRNY1ThjKo8FjORh8kBbFF71iSAn9At64x6H7a3GuP1T59Pq59zWppVIAFiYGNoH3lf6cP+kZyJ31PJxqxpnnhlFfLLpeWBFpUfr7aLiJeReJIAewOLcn9sisKZzqFai582Qu5FM9ULMatw0KNyfAKr4P+rzbS0dcmxkD1iTMYLH/tdu3TzSjKqdq+nPiclJGEBQ14PVN/BYgU8YweNuSETB2aAhqQsAi8WNsLVz94991oHY1tlPs0X5Rpx3VNLn7cKRlEo6gaNJVRDKLVgTMsxBz7OZRp3j/y+2N2eHTbGCaI84W2xGRRIjaHiFgQGfzlted4PiAPEdECAwEAAQ==";
    public static final String RSAPrivateKeyStrA = "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";
    public static final String RSAPublicKeyStrB = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEArecA5xr0tWq5tCT8yFBo0XAMYxQXyYl/+bszENgIfx0ThIJXwfulUDcgmPqia/BdtrfmoUMMln0DN91EtbjF1QYPLxzb+If4korq9SZw7sNMi20gngzGojbjvZO+p4cfauj38QXevLekHk/a9OqtX8X7N6/+ys17X29sUXUTSV/uLLdoapWjsEWgVhKtF4/khGp2+UcsIGQS+R7mBKayJo0gTZzoggzgNP7dVAEo7PhXhcdJDRG41dIAufdh2sCqwf/sBFdhGdprgZSsNEb+9qC555QiMzkI+vqRZcapjhCElGdgew1RXOKEl6f15+Abs7RYFlBjKfJNaaKN3UCoYMWkMBq63vcycw9CD5StvVVRTc0h+iaxxTGYJWE98c2Q6Wk/m68sb0RDhIeNXK0ocAufyx6FijPJWQuh2KDN81umL5Wnnp87UGIZiPUuLcCi3HtBqJh5TAEiBb/UTbWsQ8vrxsevQtzERe5DHin9su+HdlHDMH5vCCADenzc/MlUJ/YWwHwN5VS6V/hlOwWxt+ZVM6qFsbIvtop2xAh0sRmrMy7mfsuSo9p3/Rb8MdQa9zURDf77MNaEEwis+Oqd2agmZChkJx1vyPEawjnQ+IudcVd8pvxJckb7mKWBB8a6xuRYDLLHPj2XsXBizrdPYBMIgkNqR5jgY8y+dkVWZk8CAwEAAQ==";
    public static final String RSAPrivateKeyStrB = "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";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) throws Exception {
        //post请求参数,由key跟content两部分组成,key是用RSA公钥加密的AESKey,content是AES加密的原本参数
        String encryptBody = "{\"key\": \"1234567890123456\",\"content\": \"7890\"}";

        JSONObject jsonObject = JSON.parseObject(encryptBody);
        String content = String.valueOf(jsonObject.get("content"));


        String aesKey = genAesSecret(192);
        AES aes = SecureUtil.aes(aesKey.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = aes.getCipher();
        String algorithm = cipher.getAlgorithm();


        SecretKey secretKey = aes.getSecretKey();
        aesKey = new String(secretKey.getEncoded());
        System.err.println("生成aes密钥  "+aesKey);

        String encrypt1 = cn.hutool.core.codec.Base64.encode(aes.encrypt(content));
        String encrypt2 = aesEncrypt(content,aesKey);

        System.err.println("aes加密1：  "+encrypt1);
        System.err.println("aes加密2：  "+encrypt2);



        System.err.println("aes解密1：  "+new String(aes.decrypt(encrypt1)));
        System.err.println("aes解密2：  "+(aesDecrypt(encrypt2,aesKey)));
    }


    /**
     * RSA公钥加密
     */
    public static String rsaPubEncrypt(String str,String pubKeyStr) {
        String result = "";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(pubKeyStr);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            result = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * RSA公钥解密
     */
    public static String rsaPubDecrypt(String str,String pubKeyStr){
        String result = "";
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(pubKeyStr);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            result = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * RSA私钥加密
     */
    public static String rsaPriEncrypt(String str,String priKeyStr) {
        String result = "";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(priKeyStr);
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            result = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * RSA私钥解密
     */
    public static String rsaPriDecrypt(String str, String priKeyStr) {
        String result = "";
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(priKeyStr);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            result = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成随机AES密钥
     * @return
     */
    public static String genAesSecret(int aesSecretLength){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            //下面调用方法的参数决定了生成密钥的长度，可以修改为128, 192或256
            kg.init(aesSecretLength);
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String secret = Base64.getEncoder().encodeToString(b);
            return secret;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("没有此算法");
        }
    }

    /**
     * AES加密ECB模式PKCS5Padding填充方式
     */
    public static String aesEncrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
        byte[] doFinal = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(doFinal));
    }

    /**
     * AES解密ECB模式PKCS5Padding填充方式
     */
    public static String aesDecrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str));
        return new String(doFinal);
    }

}