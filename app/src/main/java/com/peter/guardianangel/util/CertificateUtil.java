package com.peter.guardianangel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class CertificateUtil {
    public static final int lengthOfNonce = 7;
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String generaterGenKey(){
        String tempString = getTimeStamp() + getRandomString();

        return "peter" + stringSHA1(tempString);
    }

    // 获取timeStamp
    public static String getTimeStamp(){
        final java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();//获取当前时间
        return format.format(date);
    }

    // 获取随机字符串
    public static String getRandomString() { //length表示生成字符串的长度
        int length = CertificateUtil.lengthOfNonce;
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String stringSHA1(String input) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            byte[] inputByteArray = input.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
//            return byteArrayToHex(resultByteArray).toUpperCase();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };

        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    private static String[] sortByDictory(String[] strs){
        for (int i = 0; i < strs.length - 1; i++) {
            boolean change = false; // 用作冒泡排序的标记，如果一趟排序存在交换，则change设为true，说明还需要下一趟排序
            for (int j = 0; j < strs.length - i - 1; j++) {
                if (bigger(strs[j], strs[j + 1])) {
                    // swap(s[j], s[j + 1]);
                    String tmp = strs[j];
                    strs[j] = strs[j + 1];
                    strs[j + 1] = tmp;
                    change = true;
                }
            }
            if (!change) {
                break; // 当change为false的时候，说明不需要再冒泡了
            }
        }
        return strs;
    }

    private static boolean bigger(String s1, String s2) {
        int length1 = s1.length();
        int length2 = s2.length();
        int i = 0;
        while (i < length1 && i < length2) {
            if (s1.charAt(i) > s2.charAt(i)) {
                return true;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                return false;
            } else {
                i++;
            }
        }
        if (i == length1) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    private static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSignature(String genkey, String timeStamp, String nonce){
        String[] strs = {genkey, timeStamp, nonce};
        String[] sortedStrs = sortByDictory(strs);
        String tempString = "";

        if (sortedStrs[0].equals(genkey)){
            tempString = genkey;
        }
        else if (sortedStrs[0].equals(nonce)){
            tempString = nonce;
        }
        else if (sortedStrs[0].equals(timeStamp)){
            tempString = timeStamp;
        }

        if (sortedStrs[1].equals(genkey)){
            tempString =  tempString +genkey;
        }
        else if (sortedStrs[1].equals(nonce)){
            tempString = tempString +nonce;
        }
        else if (sortedStrs[1].equals(timeStamp)){
            tempString = tempString + timeStamp;
        }

        if (sortedStrs[2].equals(genkey)){
            tempString = tempString +genkey;
        }
        else if (sortedStrs[2].equals(nonce)){
            tempString = tempString +nonce;
        }
        else if (sortedStrs[2].equals(timeStamp)){
            tempString = tempString +timeStamp;
        }
        return encode(tempString);
    }
}
