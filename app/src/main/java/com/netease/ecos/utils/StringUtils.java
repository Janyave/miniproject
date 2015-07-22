package com.netease.ecos.utils;

import android.annotation.SuppressLint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String EMPTY_STRING  = "";
    public static String TIME_FORMAT   = "yyyy-MM-dd HH:mm:ss"; // �����ڸ�ʽ
    final static String PLEASE_SELECT = "��ѡ��...";

    /**
     * ��Ϊ�� ������
     *
     * @param str
     *            �ַ���
     * @return ����ַ�����Ϊ���ҳ��ȴ���1 ������ ���������ؼ�
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().equals(EMPTY_STRING);
    }

    /**
     * ���Ϊ�� ������
     *
     * @param str
     *            �ַ���
     * @return ���Ϊ�ջ򳤶ȵ����㣬�����棬�������ؼ�
     */
    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * ȥ���ո�Ϊ�� ������
     *
     * @param str
     *            �ַ���
     * @return ����ַ�����Ϊ����ȥ���ո񳤶ȴ���1 ������ ���������ؼ�
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
     * �ַ���Ϊ�գ���ȥ���ո�Ϊ�գ��򷵻���
     *
     * @param str
     *            �ַ���
     * @return ����ַ���Ϊ��,��ȥ���ո񳤶�Ϊ0,�����棬�������ؼ�
     */
    public static boolean isTrimBlank(String str) {
        return str == null || str.trim().equals(EMPTY_STRING);
    }

    /**
     * ������ַ���
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    /**
     * ������ַ���
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null") || str.trim().equals("") || str.trim().equals("����ѡ��")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    /**
     * ����ĸ��д
     *
     * @param str
     *            Ҫת�����ַ���
     * @return ����ĸ��д���ַ���
     */
    @SuppressLint("DefaultLocale")
    public static String capFirstUpperCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * ����ĸСд
     *
     * @param str
     *            Ҫת�����ַ���
     * @return ����ĸСд���ַ���
     */
    @SuppressLint("DefaultLocale")
    public static String capFirstLowerCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * �Ƿ����ֻ��ַ���
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
     * �Ƿ������֤����
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
     * ȷ������Ա�
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isConfirmPassword(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * �����ֻ������м�4λ, example: 185****6666
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
     * �����˻�������м伸λ��ֻ��ʾǰ��λ�ͺ���λ,
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
     * ����email���м伸λ��ֻ��ʾ@����ǰ��λ,
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
     * ������ַ���ÿ4λ��������ӿո񣨱������п�����ȣ�
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
     * �ֻ�����3 4 4��ʽ, example: 185 6666 6666
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
     * ���ڸ�ʽת����StrתStr�� 2014-05-06 תΪ 20140506
     */
    public static String formatDate2(String str) {
        return str.replace("-", "");
    }

    /**
     * ���ڸ�ʽת����StrתStr�� 20140506תΪ2014-05-06
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
     * ʱ���ʽת����StrתStr�� 123312תΪ12:33
     */
    public static String formatTime(String str) {
        String temp = "";
        temp += str.subSequence(0, 2);
        temp += ':';
        temp += str.substring(2, 4);
        return temp;
    }

    /**
     * �������ڸ�ʽ���ַ���ת��Ϊ������ 1970-09-01 12:00:00
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

    // ����С�������λ���֣�����������������
    public static String sub2DecimalPlaceNoRoundOff(String strDouble) {
//        String str;
        int position = strDouble.indexOf("."); // ����С�����λ��
        if (position != -1) {
            if ((strDouble.length() - 1 - position) >= 2) {
                // ���С����������λ
                strDouble = strDouble.substring(0, position + 3);
                // } else {
                // // С���������λ��0
                // DecimalFormat df = new DecimalFormat("0.00");
                // strDouble = df.format(double1);
            }
        }
        return strDouble;
    }

    /**
     * ��JID�����û���
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
     * ���ݸ�����ʱ���ַ����������� �� ʱ �� ��
     *
     * @param allDate
     *            like "yyyy-MM-dd hh:mm:ss SSS"
     * @return
     */
    public static String getMonthTomTime(String allDate) {
        return allDate.substring(5, 19);
    }

    /**
     * ���ݸ�����ʱ���ַ����������� �� ʱ �� �µ�����
     *
     * @param allDate
     *            like "yyyy-MM-dd hh:mm:ss SSS"
     * @return
     */
    public static String getMonthTime(String allDate) {
        return allDate.substring(5, 16);
    }

    /**
     * ���ַ������й���,ȥ��\r\n
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
