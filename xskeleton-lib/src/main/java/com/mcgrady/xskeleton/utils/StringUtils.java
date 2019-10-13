package com.mcgrady.xskeleton.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mcgrady on 2019-08-22.
 */
public class StringUtils {

    public static String strJoiner(String separator, List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String string = iterator.next();
            if (!TextUtils.isEmpty(string)) {
                stringBuilder.append(string);
                if (iterator.hasNext()) {
                    stringBuilder.append(separator);
                }
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 汉字 转换为对应的 UTF-8编码
     * @param s
     * @return
     */
    public static String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;
                    b = Character.toString(c).getBytes("utf-8");
                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        //转换为unsigned integer  无符号integer
                    /*if (k < 0)
                        k += 256;*/
                        k = k < 0? k+256:k;
                        //返回整数参数的字符串表示形式 作为十六进制（base16）中的无符号整数
                        //该值以十六进制（base16）转换为ASCII数字的字符串
                        sb.append(Integer.toHexString(k).toUpperCase());

                        // url转置形式
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * URL 解码
     *
     * @return String
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * URL 编码
     *
     * @return String
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param str
     * @param len 字节长度
     * @param use_ellipsis
     * @return
     */
    public static String substrUTF8(String str, int len, boolean use_ellipsis) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return substrUTF8(str.getBytes(), len, use_ellipsis);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return str;
    }
    public static String substrUTF8(byte[] bytes, int len, boolean use_ellipsis)
            throws UnsupportedEncodingException {
        if (bytes == null || bytes.length == 0 || len <= 0) {
            return "";
        }
        if (len >= bytes.length) {
            return new String(bytes, "UTF-8");
        }
        int index = 0;
        while (index < len) {
            final byte b = bytes[index];
            // is ascii
            if ((b & 0x80/*0b10000000*/) == 0) {
                ++index;
            } else {
                int count = 1;
                byte t = 0x40/*0b01000000*/;
                for (int i = 1; i < 8; ++i) {
                    if ((b & t) != 0) {
                        ++count;
                        t >>>= 1;
                    } else {
                        break;
                    }
                }
                final int sum = index + count;
                if (sum <= len) {
                    index = sum;
                } else {
                    break;
                }
            }
        }

        String s = new String(bytes, 0, index, "UTF-8");
        return use_ellipsis ? s + "..." : s;
    }

    private static String byteToBinary(byte b) {
        final char[] chars = new char[8];
        byte t = 0x1/*0b10000000*/;
        for (int i = 7; i >= 0; --i) {
            if ((b & t) == 0) {
                chars[i] = '0';
            } else {
                chars[i] = '1';
            }
            t <<= 1;
        }
        return String.valueOf(chars);
    }
}
