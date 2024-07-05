package com.yunext.common.utils;


import com.google.common.collect.Lists;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StringUtil {

    private static Long randomIndex = System.currentTimeMillis() / 100000;
    private static final int TOKEN_LENGTH =48;
    private static char[] dicArray = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char[] digits = "0123456789".toCharArray();
    private static char[] lowercaseLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char[] specialCharacters = "!@#$&*".toCharArray();
    private static final Pattern ZH_PATTERN = Pattern.compile( "[\u4e00-\u9fa5]");
    private static final Pattern PHONE_PATTERN=Pattern.compile("\\+*[\\d- ]{5,32}");

    /*private static final String MOBILE_REGEX = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$";*/

    private static final Pattern MOBILE_REGEX = Pattern.compile("\\+*[\\d- ]{5,32}");
    private static final Pattern EMAIL_PATTERN=Pattern.compile("^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private static final Pattern RADIX_16_REGEX = Pattern.compile("^[A-Fa-f0-9]+$");
    private static final Pattern DIGITAL_PATTERN = Pattern.compile("[-+]?\\d+(?:\\.\\d+)?");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?[0-9]+");
    private static final Pattern MAC_PATTERN = Pattern.compile("^[A-F0-9]{2}(:[A-F0-9]{2}){5}$");
    private static final Pattern ONLY_LETTER_AND_DIGIT = Pattern.compile("^[0-9a-zA-Z]+$");
    private static final Pattern YYYY_MM_DD_PATTERN = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$");

    private final static String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };

    /**
     * 判断字符串是否为空
     *
     * @Author zhouq
     * @Date 2017/10/30 16:23
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * 判断字符串非空
     *
     * @Author zhouq
     * @Date 2017/10/30 16:24
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * @param str
     * @return
     */
    public static boolean isAnyEmpty(String... str) {
        for (String s : str) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isNoneEmpty(String... str) {
        return !isAnyEmpty(str);
    }

    /**
     * MD5加密
     *
     * @Author zhouq
     * @Date 2017/10/30 16:22
     */
    public static String encodeMD5(String s) {
        return encodeMD5(s,null);
    }

    /**
     * MD5加盐加密
     */
    public static String encodeMD5(String s,String salt) {
        if (s == null || s.trim().length() == 0) {
            return null;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        md.update(s.getBytes());
        if(isNotEmpty(salt)){
            md.update(salt.getBytes());
        }
        byte[] datas = md.digest();
        int len = datas.length;
        char[] str = new char[len * 2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte byte0 = datas[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }


    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 判断是否包含符号或者汉字
     *
     * @Author zhouq
     * @Date 2017/10/30 16:22
     */
    public static boolean ifContainSymbolOrChinese(String input) {
        for (int i = 0; i < input.length(); i++) {//符号
            char bb = input.charAt(i);
            if ("`¬!\"£$%^*()~=#{}[];':,./?/*-_+，·@#￥……&。、‘！".contains(bb + "")) {
                return false;
            }
        }
        Matcher matcher = ZH_PATTERN.matcher(input);//汉字
        if (matcher.find()) {
            return false;
        }
        return true;
    }


    /**
     * 判断是否全都为数字
     *
     * @Author zhouq
     * @Date 2017/10/30 16:20
     */
    public static boolean ifAllNumbers(String input) {
        return input.matches("[0-9]+");
    }


    /**
     * 获取随机字符串
     *
     * @Author zhouq
     * @Date 2017/10/30 16:19
     */
    public static String randomString(int length) {
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    public static String randomPwdLength8(){
        Random random = new Random();
        StringBuilder password = new StringBuilder(8);
        //5位数字
        for (int i = 0; i < 5; i++) {
            password.append(digits[random.nextInt(digits.length)]);
        }
        //1位特殊符号
        password.append(specialCharacters[random.nextInt(specialCharacters.length)]);
        //2位字母
        for (int i = 0; i < 2; i++) {
            password.append(lowercaseLetters[random.nextInt(lowercaseLetters.length)]);
        }
        return password.toString();
    }

    /**
     * 获取随机数字字符串
     *
     * @Author zhouq
     * @Date 2017/10/30 16:19
     */
    public static String randomNumberString(int length) {
        String ALLCHAR = "0123456789";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 获取文件的后缀名
     *
     * @param filename
     * @return
     */
    public static String getExt(String filename) {
        String ext = null;
        int index = filename.lastIndexOf(".");
        if (index > 0) {
            ext = filename.substring(index + 1).toLowerCase();
        }
        return ext;
    }


    /**
     * 本地安全的 随机数算法，最小长度为 5，小于5的会忽略
     *
     * @return
     */
    public synchronized static String localSafeRandomString(int len) {
        randomIndex++;
        StringBuffer sb = new StringBuffer(Long.toString(randomIndex, 36));
        while (len > sb.length()) {
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * 去空白
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null) {
            return "";
        } else {
            return str.trim();
        }
    }

    /**
     * 如果不是手机号则抛出异常
     * @param mobile 手机号
     */
    public static boolean isMobile(String mobile){
        Matcher matcher = MOBILE_REGEX.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 邮箱正则校验
     * @param email
     * @return
     */
    public static boolean emailCheck(String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * 日期格式yyyy-mm-dd正则校验
     * @param dateTime
     * @return
     */
    public static boolean dateCheck(String dateTime){
        return isNotEmpty(dateTime) && YYYY_MM_DD_PATTERN.matcher(dateTime).matches();
    }
    /**
     * 格式化字符串，左边补0，长度一共为len
     *
     * @param str
     * @return
     */
    public static String formatStr(String str, int size) {
        StringBuilder sb = new StringBuilder(str);
        int len = sb.length();
        for (int i = 0; i < size - len; i++) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }


    public static String valueOf(Object obj) {
        if (obj == null) {
            return "";
        }
        if ((obj instanceof String)) {
            String strObj = obj.toString().trim();
            if ("null".equalsIgnoreCase(strObj)) {
                return "";
            }
            return strObj;
        }
        if ((obj instanceof BigDecimal)) {
            BigDecimal bigObj = (BigDecimal) obj;
            return bigObj.toString();
        }

        return obj.toString().trim();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public synchronized static String initTokenId() {
        StringBuffer tk = new StringBuffer(uuid());
        int l = tk.length();
        for (int i = l; i < TOKEN_LENGTH; i++) {
            int x = new Random().nextInt(36);
            tk.append(dicArray[x % dicArray.length]);
        }
        return tk.toString().toUpperCase();
    }

    /**
     * 国际手机号码正则验证
     * @param phone
     * @return
     */
    public static boolean internationalPhoneCheck(String phone){
        Matcher matcher = MOBILE_REGEX.matcher(phone);
        return matcher.matches();
    }

    /**
     * 判断是否為二進制字符串
     *
     */
    public static boolean isBinaryCode(String input) {
        return input.matches("[0-1]+");
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 是否为字符串数字
     * @param cs
     * @return
     */
    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 不全部为空
     * @param str
     * @return
     */
    public static boolean isNotAllEmpty(String... str) {
        return Lists.newArrayList(str).stream().anyMatch(StringUtil::isNotEmpty);
    }

    public static boolean isHexCode(String input) {
        return RADIX_16_REGEX.matcher(input).matches();
    }

    /**
     * 判断字符串是否是数字，包括正数、负数、浮点数
     * @param digital
     * @return
     */
    public static boolean isDigital(String digital){
        Matcher matcher = DIGITAL_PATTERN.matcher(digital);
        return isNotEmpty(digital) && matcher.matches();
    }

    /**
     * 判断字符串是否是整数，包括正负整数
     * @param number
     * @return
     */
    public static boolean isInteger(String number){
        Matcher matcher = INTEGER_PATTERN.matcher(number);
        return isNotEmpty(number) && matcher.matches();
    }

    /**
     * 判断字符串是否MAC地址
     * @param mac
     * @return
     */
    public static boolean isMAC(String mac){
        Matcher matcher = MAC_PATTERN.matcher(mac);
        return isNotEmpty(mac) && matcher.matches();
    }

    /**
     * MongoDB的regex查询，对输入特殊字符转义
     * @param keyword
     * @return
     */
    public static String escapeSpecialWord(String keyword) {
        if (isNotEmpty(keyword)) {
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static boolean onlyLetterAndDigit(String orderNo) {
        Matcher matcher = ONLY_LETTER_AND_DIGIT.matcher(orderNo);
        return isNotEmpty(orderNo) && matcher.matches();
    }

    public static String removeWhitespace(String str) {
        if(isEmpty(str)) {
            return str;
        }
        return str.replaceAll("\\s+", "");
    }

    public static <T> String concat(T obj, String replace, String concat) {
        return Objects.isNull(obj) ? (replace) : (obj + concat);
    }

    public static int compareDigital(String digital1, String digital2, String remove) {
        digital1 = digital1.replaceAll(remove, "");
        digital2 = digital2.replaceAll(remove, "");
        Double d1 = Double.parseDouble(digital1);
        Double d2 = Double.parseDouble(digital2);
        return d1.compareTo(d2);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : repeat(padChar, pads).concat(str);
            }
        }
    }

    public static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return "";
        } else {
            char[] buf = new char[repeat];
            Arrays.fill(buf, ch);
            return new String(buf);
        }
    }
}
