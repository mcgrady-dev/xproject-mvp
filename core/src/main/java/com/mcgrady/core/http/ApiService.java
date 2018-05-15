package com.mcgrady.core.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <p></p>
 *
 * @author: mcgrady
 * @date: 2018/5/9
 */
public interface ApiService {

    @GET
    Call<ResponseBody> executeGet(@Url String url);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> executePost(@Url String url, @FieldMap Map<String, String> map);

    /**
     * 流式下载 (不加@Streaming注解的话,会整个文件字节数组全部加载进内存,可能导致oom)
     * @param fileUrl
     * @param headers

     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl, @HeaderMap Map<String, String> headers);
}
