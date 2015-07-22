package com.netease.ecos.utils;

import android.annotation.SuppressLint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String EMPTY_STRING  = "";
    public static String TIME_FORMAT   = "yyyy-MM-dd HH:mm:ss"; // 长日期格式
    final static String PLEASE_SELECT = "请选择...";

    /**
     * 不为空 返回真
     *
     * @param str
     *            字符串
     * @return 如果字符串不为空且长度大于1 返回真 ，其他返回假
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().equals(EMPTY_STRING);
    }

    /**
     * 如果为空 返回真
     *
     * @param str
     *            字符串
     * @return 如果为空或长度等于零，返回真，其他返回假
     */
    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 去掉空格不为空 返回真
     *
     * @param str
     *            字符串
     * @return 如果字符串不为空且去掉空格长度大于1 返回真 ，其他返回假
     */
    public static boolean isNotTrimBlank(String str) {
        return str != null && !str.trim().equals(EMPTY_STRING);
    }

    public static boolean empty(Object o) {
        return o == null || "".equals(o.toString().trim()) || "null".equalsIgnoreCase(o.toString().trim())
                || "undefined".equalsIgnoreCase(o.toString().trim()) || PLEASE_SELECT.equals(o.toString().trim());
    }

    public static boolean notEmpty(Object o) {
        return o != null && !"".equals(o.toString().trim()) && !"null".equalsIgnoreCase(o.toString().trim())
                && !"undefined".equalsIgnoreCase(o.toString().trim()) && !PLEASE_SELECT.equals(o.toString().trim());
    }

    /**
     * 字符串为空，或去掉空格为空，则返回真
     *
     * @param str
     *            字符串
     * @return 如果字符串为空,或去掉空格长度为0,返回真，其他返回假
     */
    public static boolean isTrimBlank(String str) {
        return str == null || str.trim().equals(EMPTY_STRING);
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null") || str.trim().equals("") || str.trim().equals("－请选择－")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    /**
     * 首字母大写
     *
     * @param str
     *            要转换的字符串
     * @return 首字母大写的字符串
     */
    @SuppressLint("DefaultLocale")
    public static String capFirstUpperCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     *            要转换的字符串
     * @return 首字母小写的字符串
     */
    @SuppressLint("DefaultLocale")
    public static String capFirstLowerCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 是否是手机字符串
     *
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str) {
        Pattern p = Pattern.compile("^((\\+?86)|((\\+86)))?1\\d{10}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否是身份证号码
     *
     * @param str
     * @return
     */
    public static boolean isIdCardNumber(String str) {
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 确认密码对比
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isConfirmPassword(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 隐藏手机号码中间4位, example: 185****6666
     *
     * @param phone
     * @return
     */
    public static String hiddenPhoneNum(final String phone) {
        if (isPhoneNumber(phone)) {
            char[] mobile = phone.toCharArray();
            for (int i = 3; i < 7; i++) {
                mobile[i] = '*';
            }
            return String.valueOf(mobile);
        }
        return "";
    }

    /**
     * 隐藏账户号码的中间几位，只显示前三位和后四位,
     * example: 882202010000201 --->  88220********201
     *
     * @param phone
     * @return
     */
    public static String hiddenSevralNum(final String str) {
        char[] number = str.toCharArray();
        int len = number.length;
        for (int i = 3; i < len - 4; i++) {
            number[i] = '*';
        }
        return String.valueOf(number);
    }

    /**
     * 隐藏email的中间几位，只显示@符号前三位,
     * example: 13849620635@126.com --->  126********@126.com
     *
     * @param phone
     * @return
     */
    public static String hiddenEmail(final String str) {
        char[] email = str.toCharArray();
        int len = email.length;
        for (int i = 3; i < len; i++) {
            if (email[i] == '@') {
                break;
            }
            email[i] = '*';
        }
        return String.valueOf(email);
    }

    /**
     * 输入的字符串每4位隔开并添加空格（比如银行卡号码等）
     */
    public static String add4blank(String str) {
        str = str.replace(" ", "");
        int strLength = str.length() / 4;
        String temp = "";
        for (int i = 0; i < strLength; i++) {
            temp += str.substring(i * 4, (i + 1) * 4);
            temp += " ";
        }
        temp += str.substring(strLength * 4);
        return temp;
    }

    /**
     * 手机号码3 4 4格式, example: 185 6666 6666
     */
    public static String addmobileblank(String str) {
        if (str.replace(" ", "").length() != 11)
            return str;
        String temp = "";
        temp += str.subSequence(0, 3);
        temp += ' ';
        temp += str.substring(3, 7);
        temp += ' ';
        temp += str.substring(7);
        return temp;
    }

    /**
     * 日期格式转换（Str转Str） 2014-05-06 转为 20140506
     */
    public static String formatDate2(String str) {
        return str.replace("-", "");
    }

    /**
     * 日期格式转换（Str转Str） 20140506转为2014-05-06
     */
    public static String formatDate(String str) {
        if (str.replace("-", "").length() != 8)
            return str;
        String temp = "";
        temp += str.subSequence(0, 4);
        temp += '-';
        temp += str.substring(4, 6);
        temp += '-';
        temp += str.substring(6);
        return temp;
    }

    /**
     * 时间格式转换（Str转Str） 123312转为12:33
     */
    public static String formatTime(String str) {
        String temp = "";
        temp += str.subSequence(0, 2);
        temp += ':';
        temp += str.substring(2, 4);
        return temp;
    }

    /**
     * 将长日期格式的字符串转换为长整型 1970-09-01 12:00:00
     *
     * @param date
     * @param format
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long convert2long(String date) {
        try {
            if (isNotBlank(date)) {
                SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
                return sf.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static boolean num(Object o) {
        int n = 0;
        try {
            n = Integer.parseInt(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean decimal(Object o) {
        double n = 0;
        try {
            n = Double.parseDouble(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (n > 0.0) {
            return true;
        } else {
            return false;
        }
    }

    // 保留小数点后两位数字，但不进行四舍五入
    public static String sub2DecimalPlaceNoRoundOff(String strDouble) {
//        String str;
        int position = strDouble.indexOf("."); // 计算小数点的位置
        if (position != -1) {
            if ((strDouble.length() - 1 - position) >= 2) {
                // 如果小数点后多于两位
                strDouble = strDouble.substring(0, position + 3);
                // } else {
                // // 小数点后不足两位补0
                // DecimalFormat df = new DecimalFormat("0.00");
                // strDouble = df.format(double1);
            }
        }
        return strDouble;
    }

    /**
     * 给JID返回用户名
     *
     * @param Jid
     * @return
     */
    public static String getUserNameByJid(String Jid) {
        if (empty(Jid)) {
            return null;
        }
        if (!Jid.contains("@")) {
            return Jid;
        }
        return Jid.split("@")[0];
    }

    /**
     * 根据给定的时间字符串，返回月 日 时 分 秒
     *
     * @param allDate
     *            like "yyyy-MM-dd hh:mm:ss SSS"
     * @return
     */
    public static String getMonthTomTime(String allDate) {
        return allDate.substring(5, 19);
    }

    /**
     * 根据给定的时间字符串，返回月 日 时 分 月到分钟
     *
     * @param allDate
     *            like "yyyy-MM-dd hh:mm:ss SSS"
     * @return
     */
    public static String getMonthTime(String allDate) {
        return allDate.substring(5, 16);
    }

    /**
     * 对字符串进行过滤,去除\r\n
     * 
     * @param str
     * @return
     */
    public static String filter(String str) {
        return str.replace("\\r\\n", "");
    }
    
    
    public static String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

    private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
