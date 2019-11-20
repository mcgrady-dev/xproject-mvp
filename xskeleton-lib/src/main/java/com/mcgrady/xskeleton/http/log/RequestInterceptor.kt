package com.mcgrady.xskeleton.http.log

import android.annotation.SuppressLint
import com.blankj.utilcode.util.LogUtils
import com.mcgrady.xskeleton.http.handler.HttpHandler
import com.mcgrady.xskeleton.utils.CharacterHandler
import com.mcgrady.xskeleton.utils.UrlEncoderUtils
import com.mcgrady.xskeleton.utils.ZipHelper
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Created by mcgrady on 2019-08-12.
 */
class RequestInterceptor @JvmOverloads constructor(private val printLevel: Level, private val formatPrinter: FormatPrinter, private val httpHandler: HttpHandler? = null) : Interceptor {

    enum class Level {
        /**
         * 不打印log
         */
        NONE,
        /**
         * 只打印请求信息
         */
        REQUEST,
        /**
         * 只打印响应信息
         */
        RESPONSE,
        /**
         * 所有数据全部打印
         */
        ALL
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val logRequest = printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.REQUEST
        if (logRequest) { //打印请求信息
            if (request.body() != null && isParseable(request.body()!!.contentType())) {
                formatPrinter.printJsonRequest(request, parseParams(request))
            } else {
                formatPrinter.printFileRequest(request)
            }
        }
        val logResponse = printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.RESPONSE
        val t1 = if (logResponse) System.nanoTime() else 0
        val originalResponse: Response
        originalResponse = try {
            chain.proceed(request)
        } catch (e: Exception) {
            LogUtils.w("Http Error: $e")
            throw e
        }
        val t2 = if (logResponse) System.nanoTime() else 0
        val responseBody = originalResponse.body()
        //打印响应结果
        var bodyString: String? = null
        if (responseBody != null && isParseable(responseBody.contentType())) {
            bodyString = printResult(request, originalResponse, logResponse)
        }
        if (logResponse) {
            val segmentList = request.url().encodedPathSegments()
            val header = originalResponse.headers().toString()
            val code = originalResponse.code()
            val isSuccessful = originalResponse.isSuccessful
            val message = originalResponse.message()
            val url = originalResponse.request().url().toString()
            if (responseBody != null && isParseable(responseBody.contentType())) {
                formatPrinter.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1), isSuccessful,
                        code, header, responseBody.contentType(), bodyString, segmentList, message, url)
            } else {
                formatPrinter.printFileResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful, code, header, segmentList, message, url)
            }
        }
        return httpHandler?.onHttpResultResponse(bodyString, chain, originalResponse)
                ?: originalResponse
    }

    /**
     * 打印响应结果
     *
     * @param request     [Request]
     * @param response    [Response]
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun printResult(request: Request, response: Response, logResponse: Boolean): String? {
        return try { //读取服务器返回的结果
            val responseBody = response.newBuilder().build().body()
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            //获取content的压缩类型
            val encoding = response
                    .headers()["Content-Encoding"]
            val clone = buffer.clone()
            //解析response content
            parseContent(responseBody, encoding, clone)
        } catch (e: IOException) {
            e.printStackTrace()
            "{\"error\": \"" + e.message + "\"}"
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody [ResponseBody]
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private fun parseContent(responseBody: ResponseBody?, encoding: String?, clone: Buffer): String? {
        var charset = Charset.forName("UTF-8")
        val contentType = responseBody!!.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        return if (encoding != null && encoding.equals("gzip", ignoreCase = true)) { //content 使用 gzip 压缩
            ZipHelper.Companion.decompressForGzip(clone.readByteArray(), convertCharset(charset)) //解压
        } else if (encoding != null && encoding.equals("zlib", ignoreCase = true)) { //content 使用 zlib 压缩
            ZipHelper.Companion.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset)) //解压
        } else { //content 没有被压缩, 或者使用其他未知压缩方式
            clone.readString(charset)
        }
    }

    companion object {
        /**
         * 解析请求服务器的请求参数
         *
         * @param request [Request]
         * @return 解析后的请求信息
         * @throws UnsupportedEncodingException
         */
        @Throws(UnsupportedEncodingException::class)
        fun parseParams(request: Request): String {
            return try {
                val body = request.newBuilder().build().body() ?: return ""
                val requestbuffer = Buffer()
                body.writeTo(requestbuffer)
                var charset = Charset.forName("UTF-8")
                val contentType = body.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                var json = requestbuffer.readString(charset)
                if (UrlEncoderUtils.Companion.hasUrlEncoded(json)) {
                    json = URLDecoder.decode(json, convertCharset(charset))
                }
                CharacterHandler.Companion.jsonFormat(json)
            } catch (e: IOException) {
                e.printStackTrace()
                "{\"error\": \"" + e.message + "\"}"
            }
        }

        /**
         * 是否可以解析
         *
         * @param mediaType [MediaType]
         * @return `true` 为可以解析
         */
        fun isParseable(mediaType: MediaType?): Boolean {
            return if (mediaType?.type() == null) false else isText(mediaType) || isPlain(mediaType)
                    || isJson(mediaType) || isForm(mediaType)
                    || isHtml(mediaType) || isXml(mediaType)
        }

        fun isText(mediaType: MediaType?): Boolean {
            return if (mediaType?.type() == null) false else {
                mediaType.type() == "text"
            }
        }

        @SuppressLint("DefaultLocale")
        fun isPlain(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else {
                mediaType.subtype().toLowerCase().contains("plain")
            }
        }

        @SuppressLint("DefaultLocale")
        fun isJson(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else {
                mediaType.subtype().toLowerCase().contains("json")
            }
        }

        @SuppressLint("DefaultLocale")
        fun isXml(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else {
                mediaType.subtype().toLowerCase().contains("xml")
            }
        }

        @SuppressLint("DefaultLocale")
        fun isHtml(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else {
                mediaType.subtype().toLowerCase().contains("html")
            }
        }

        @SuppressLint("DefaultLocale")
        fun isForm(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else {
                mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded")
            }
        }

        fun convertCharset(charset: Charset?): String {
            val s = charset.toString()
            val i = s.indexOf("[")
            return if (i == -1) s else s.substring(i + 1, s.length - 1)
        }
    }

}