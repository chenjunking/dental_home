package com.dental.home.app.utils;


import cn.hutool.core.util.StrUtil;

/**
 * 脱敏工具类
 */
public class PrivacyUtil {

    //银行账户：显示前六后四，范例：622848******4568
    public static String encryptBankAcct(String bankAcct) {
        if (bankAcct == null) {
            return "";
        }
        return replaceBetween(bankAcct, 6, bankAcct.length() - 4, null);
    }

    //银行账户：显示前后四，范例：****4568
    public static String encryptBankAcctFour(String bankAcct) {
        if (bankAcct == null) {
            return "";
        }
        if((bankAcct.length() - 4) < 0){
            return "";
        }
        return "****" + bankAcct.substring(bankAcct.length() - 4, bankAcct.length());
    }

    //身份证号码：显示前六后四，范例：110601********2015
    public static String encryptIdCard(String idCard) {
        if (idCard == null) {
            return "";
        }
        return replaceBetween(idCard, 6, idCard.length() - 4, null);
    }

    //手机：显示前三后四，范例：189****3684
    public static String encryptPhoneNo(String phoneNo) {
        if (phoneNo == null) {
            return "";
        }
        return replaceBetween(phoneNo, 3, phoneNo.length() - 4, null);
    }

    //姓名：显示前一后一，范例：189****3684
    public static String encryptUsername(String userName) {

        if (StrUtil.isBlank(userName)) {
            return "";
        }
        if(userName.length()<=1){
            return userName;
        }
        if(userName.length()==2){
            return replaceBetween(userName, 1, userName.length(), null);
        }
        if(userName.length()>2){
            return replaceBetween(userName, 1, userName.length() - 1, null);
        }
        return "";
    }


    /**
     * 将字符串开始位置到结束位置之间的字符用指定字符替换
     *
     * @param sourceStr   待处理字符串
     * @param begin       开始位置
     * @param end         结束位置
     * @param replacement 替换字符
     * @return
     */
    private static String replaceBetween(String sourceStr, int begin, int end, String replacement) {
        if (sourceStr == null) {
            return "";
        }
        if (replacement == null) {
            replacement = "*";
        }
        int replaceLength = end - begin;
        if (StrUtil.isNotBlank(sourceStr) && replaceLength > 0) {
            StringBuilder sb = new StringBuilder(sourceStr);
            sb.replace(begin, end, StrUtil.repeat(replacement, replaceLength));
            return sb.toString();
        } else {
            return sourceStr;
        }
    }
}
