package cn.ovea_y.javase_class15c.util.security;

import java.io.*;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Properties;

/**
 * @author QiangweiLuo
 */
public class RSA {
    private static String publicKeyFilePath = "RSA_pub";
    private static String privateKeyFilePath = "RSA_priv";
    private static int keyLong = 1024;

    static {
        // 加载配置文件
        Properties props = new Properties();
        try {
            props.load(RSA.class.getClassLoader().getResourceAsStream("/config/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("配置文件读取失败");
        }
        if(props.getProperty("key_pub") != null) {
            publicKeyFilePath = RSA.class.getResource("/").getPath() + props.getProperty("key_pub");
        }
        if(props.getProperty("key_pri") != null) {
            privateKeyFilePath = RSA.class.getResource("/").getPath() + props.getProperty("key_pri");
        }
        if(props.getProperty("key_long") != null) {
            keyLong = Integer.parseInt(props.getProperty("key_long"));
        }
    }

    public static String getPublicKeyFilePath() {
        return publicKeyFilePath;
    }

    public static void setPublicKeyFilePath(String publicKeyFilePath) {
        RSA.publicKeyFilePath = publicKeyFilePath;
    }

    public static String getPrivateKeyFilePath() {
        return privateKeyFilePath;
    }

    public static void setPrivateKeyFilePath(String privateKeyFilePath) {
        RSA.privateKeyFilePath = privateKeyFilePath;
    }

    public static int getKeyLong() {
        return keyLong;
    }

    public static void setKeyLong(int keyLong) {
        RSA.keyLong = keyLong;
    }

    public static String enCoding(String text){
        // 首部加入0，防止数据变成负数，负数加解密时会出问题
        text = "0" + text;
        try {
            //从文件中读取公钥
            FileInputStream f = new FileInputStream(publicKeyFilePath);
            ObjectInputStream b = new ObjectInputStream(f);
            RSAPublicKey pbk = (RSAPublicKey) b.readObject();

            //RSA算法是使用整数进行加密的，再RSA公钥中包含有两个整数信息：e和n。对于明文数字m，计算密文的公式是m的e次方再与n求模。
            BigInteger e = pbk.getPublicExponent();
            BigInteger n = pbk.getModulus();

            //获取明文的大整数
            byte ptext[] = text.getBytes("UTF8");
            BigInteger m = new BigInteger(ptext);

            //加密明文
            BigInteger c = m.modPow(e, n);
            b.close();
            f.close();
            //返回c
            return c.toString(16);
        }catch (EOFException  e){
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deCoding(String text){
        BigInteger c = new BigInteger(text, 16);
        try{
            //获取私钥
            FileInputStream f = new FileInputStream(privateKeyFilePath);
            ObjectInputStream b = new ObjectInputStream(f);
            RSAPrivateKey prk = (RSAPrivateKey) b.readObject();

            //获取私钥的参数d，n
            BigInteger d = prk.getPrivateExponent();
            BigInteger n = prk.getModulus();

            //解密明文
            BigInteger m = c.modPow(d, n);

            //计算明文对应的字符串并输出
            byte[] mt = m.toByteArray();
            //System.out.println("PlainText is ");

            StringBuilder sb = new StringBuilder(new String(mt, "UTF-8"));
            b.close();
            f.close();
            // 去除添加在首部的0
            return sb.deleteCharAt(0).toString();
        }catch (EOFException  e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
