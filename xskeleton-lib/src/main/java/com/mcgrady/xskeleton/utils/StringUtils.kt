package com.mcgrady.xskeleton.utils

/**
 * Created by mcgrady on 2019-08-22.
 */
object StringUtils {
//    fun strJoiner(separator: String?, list: List<String?>): String {
//        val stringBuilder = StringBuilder()
//        val iterator = list.iterator()
//        while (iterator.hasNext()) {
//            val string = iterator.next()
//            if (!TextUtils.isEmpty(string)) {
//                stringBuilder.append(string)
//                if (iterator.hasNext()) {
//                    stringBuilder.append(separator)
//                }
//            }
//        }
//        return stringBuilder.toString()
//    }
//
//    /**
//     * 汉字 转换为对应的 UTF-8编码
//     * @param s
//     * @return
//     */
//    fun convertStringToUTF8(s: String?): String? {
//        if (s == null || s == "") {
//            return null
//        }
//        val sb = StringBuffer()
//        try {
//            var c: Char
//            for (i in 0 until s.length) {
//                c = s[i]
//                if (c.toInt() >= 0 && c.toInt() <= 255) {
//                    sb.append(c)
//                } else {
//                    var b: ByteArray
//                    b = Character.toString(c).toByteArray(charset("utf-8"))
//                    for (j in b.indices) {
//                        var k = b[j].toInt()
//                        //转换为unsigned integer  无符号integer
///*if (k < 0)
//                        k += 256;*/k = if (k < 0) k + 256 else k
//                        //返回整数参数的字符串表示形式 作为十六进制（base16）中的无符号整数
////该值以十六进制（base16）转换为ASCII数字的字符串
//                        sb.append(Integer.toHexString(k).toUpperCase())
//                        // url转置形式
//// sb.append("%" +Integer.toHexString(k).toUpperCase());
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return sb.toString()
//    }
//
//    /**
//     * URL 解码
//     *
//     * @return String
//     */
//    fun getURLDecoderString(str: String?): String {
//        var result = ""
//        if (null == str) {
//            return ""
//        }
//        try {
//            result = URLDecoder.decode(str, "UTF-8")
//        } catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//        return result
//    }
//
//    /**
//     * URL 编码
//     *
//     * @return String
//     */
//    fun getURLEncoderString(str: String?): String {
//        var result = ""
//        if (null == str) {
//            return ""
//        }
//        try {
//            result = URLEncoder.encode(str, "UTF-8")
//        } catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//        return result
//    }
//
//    /**
//     *
//     * @param str
//     * @param len 字节长度
//     * @param use_ellipsis
//     * @return
//     */
//    fun substrUTF8(str: String, len: Int, use_ellipsis: Boolean): String {
//        if (!TextUtils.isEmpty(str)) {
//            try {
//                return substrUTF8(str.toByteArray(), len, use_ellipsis)
//            } catch (e: UnsupportedEncodingException) {
//                e.printStackTrace()
//            }
//        }
//        return str
//    }
//
//    @Throws(UnsupportedEncodingException::class)
//    fun substrUTF8(bytes: ByteArray?, len: Int, use_ellipsis: Boolean): String {
//        if (bytes == null || bytes.size == 0 || len <= 0) {
//            return ""
//        }
//        if (len >= bytes.size) {
//            return String(bytes, Charset.forName("UTF-8"))
//        }
//        var index = 0
//        while (index < len) {
//            val b = bytes[index]
//            // is ascii
//            if (b and 0x80 /*0b10000000*/ == 0) {
//                ++index
//            } else {
//                var count = 1
//                var t: Byte = 0x40 /*0b01000000*/
//                for (i in 1..7) {
//                    if (b and t != 0) {
//                        ++count
//                        t = t ushr 1
//                    } else {
//                        break
//                    }
//                }
//                val sum = index + count
//                index = if (sum <= len) {
//                    sum
//                } else {
//                    break
//                }
//            }
//        }
//        val s = String(bytes, 0, index, "UTF-8")
//        return if (use_ellipsis) "$s..." else s
//    }
//
//    private fun byteToBinary(b: Byte): String {
//        val chars = CharArray(8)
//        var t: Byte = 0x1 /*0b10000000*/
//        for (i in 7 downTo 0) {
//            if (b and t == 0) {
//                chars[i] = '0'
//            } else {
//                chars[i] = '1'
//            }
//            t = t shl 1
//        }
//        return String(chars)
//    }
}