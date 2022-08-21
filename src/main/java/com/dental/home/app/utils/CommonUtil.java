package com.dental.home.app.utils;


import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author junking
 */
public class CommonUtil {

    /**
     * 将字符串进行MD5 加密
     * @param s 源字符串
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串进行base64处理
     *
     * @param data
     * @return
     */
    public final static String base64Encode(byte[] data) {

        char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
                '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }


    /**
     * 通过正则表达式校验字符串是否有效
     * @param targetString 验证的目标字符串
     * @param regular    正则表达式
     * @return   boolean
     * @author guijinyang
     */
    public static boolean checkStringByRegular(String targetString, String regular) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(regular);
            Matcher matcher = regex.matcher(targetString);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     * @param mobileNumber
     * @return mobileNumber
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        return checkStringByRegular(mobileNumber, regex);
    }

    /**
     * 验证座机和手机号码
     * @param  phoneNumber 号码字符串
     * @return boolean
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        String regex = "^1\\d{10}$";
        return checkStringByRegular(phoneNumber, regex);
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return checkStringByRegular(email, regex);
    }

    /**
     * 验证身份证号码
     * @param CID 身份证号
     * @return
     */
    public static boolean checkCIDNumber(String CID) {
        String regex = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
        return checkStringByRegular(CID, regex);
    }

    /**
     * 验证正整数
     */
    public static boolean isPlusNumber(String number) {
        String regex = "^[1-9]d*$";
        return checkStringByRegular(number, regex);
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String fetchIpByRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    //生成随机数字和字母
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 截取前端显示的LOGO
     * @param fileUrl 文件存储路径
     * @return
     */
    public static String getFileNameByUrl(String fileUrl) {
        if (!StrUtil.isEmpty(fileUrl) && fileUrl.indexOf(File.separator) != -1) {
            return fileUrl.substring(fileUrl.lastIndexOf(File.separator) + 1);
        } else {
            return "";
        }
    }

    /**
     * 获取上传文件名
     * @param fullFileName 上传文件全名
     * @return
     */
    public static String getFileName(String fullFileName) {
        if (!StrUtil.isEmpty(fullFileName) && fullFileName.indexOf(".") != -1) {
            String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
            return fileName;
        } else {
            return "";
        }
    }

    /**
     * 生成uuid
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
                + str.substring(24);
        return temp;
    }

    /**
     * 拆分集合
     * @param <T>
     * @param resList  要拆分的集合
     * @param count    每个集合的元素个数
     * @return  返回拆分后的各个集合
     */
    public static <T> List<List<T>> split(List<T> resList, int count) {

        if (resList == null || count < 1){
            return null;
        }
        List<List<T>> ret = new ArrayList<List<T>>();
        int size = resList.size();
        //数据量不足count指定的大小
        if (size <= count) {
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }

    /**
     * list去重
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * decimal转换为数字，两位小数
     * @param value
     * @return
     */
    public static String bigDecimalToStr(final BigDecimal value) {
        if (value == null) {
            return "0";
        }

        DecimalFormat df = new DecimalFormat("#0.00");
        String str = df.format(value);

        return str;
    }


    /**
     * 过滤字符串emoji
     *
     * @param str
     * @return
     */
    public static String removeNonBmpUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        return str;
    }

    public static String getRedirectUrl(String outsideHost, String path){
        if(StrUtil.isBlank(outsideHost)){
            return "redirect:" + path;
        }else {
            return "redirect:" + outsideHost + path;
        }
    }

    /**
     * 判断是否是金额
     * @param str
     * @return
     */
    public static boolean isAmount(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        return match.matches();
    }


    /**
     * 检查字符串是否是uuid 只验证长度
     *
     * @param uuidStr
     * @return 是返回true,不是返回false
     */
    public static boolean checkIsUuid(String uuidStr) {
        try {
            if(StrUtil.isBlank(uuidStr)){
                return false;
            }
            if(uuidStr.length() != 32){
                return false;
            }
            //正则匹配
            if(!uuidStr.matches("[0-9a-zA-Z]{32}")){
                return false;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据文件存储路径 截取文件存储名称
     * @param fileUrl 文件存储路径
     * @return 返回文件存储名称
     */
    public static String getFileNameByFileUrl(String fileUrl) {
        String separator = File.separator;

        String fileName;
        if (StrUtil.isNotBlank(fileUrl) && fileUrl.indexOf(separator) != -1) {
            fileName = fileUrl.substring(fileUrl.lastIndexOf(separator) + 1);
        } else {
            fileName = "";
        }

        return fileName;
    }

}
