package com.dental.home.app.utils;

import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.Objects;

/**
 * @author junking
 */
@SuppressWarnings(value = "Duplicates")
public class ImageUtil {

    //图片转byte数组
    public static byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    //图片转byte数组
    public static byte[] image2byte(File img) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(img);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }


    /**
     * byte数组到图片
     * @param data
     * @param path
     */
    public static void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    /**
     * byte数组到16进制字符串
     * @param data
     * @return string
     */
    public static String byte2string(byte[] data) {
        if (data == null || data.length <= 1) {
            return "0x";
        }
        if (data.length > 200000) {
            return "0x";
        }
        StringBuffer sb = new StringBuffer();
        int[] buf = new int[data.length];
        // byte数组转化成十进制
        for (int k = 0; k < data.length; k++) {
            buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
        }
        // 十进制转化成十六进制
        for (int k = 0; k < buf.length; k++) {
            if (buf[k] < 16) {
                sb.append("0" + Integer.toHexString(buf[k]));
            } else {
                sb.append(Integer.toHexString(buf[k]));
            }
        }
        return "0x" + sb.toString().toUpperCase();
    }

    /**
     * 本地图片转换Base64的方法
     * @param imgPath     
     */

    public static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode(Objects.requireNonNull(data));
        // 返回Base64编码过的字节数组字符串
//        System.out.println("本地图片转换Base64:" + str);
        return str;
    }


}
