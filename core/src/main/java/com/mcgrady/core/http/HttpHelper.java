package com.mcgrady.core.http;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcgrady.core.http.factory.StringConverterFactory;
import com.mcgrady.core.http.interceptor.LoggerInterceptor;
import com.mcgrady.core.http.interceptor.TokenInterceptor;
import com.mcgrady.core.utils.FileUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>网络请求辅助类</p>
 *
 * @author: mcgrady
 * @date: 2018/5/10
 */

public class HttpHelper implements IHttpHelper {

    private static final String TAG = HttpHelper.class.getSimpleName();

    private static NetConfig sNetConfig = new NetConfig();
    private HashMap<String, Object> mServiceMap;
    private static OkHttpClient sOkHttpClient = null;
    private static Retrofit sRetrofit = null;

    public Context context;
    private Gson gson;


    public HttpHelper(Context context) {
        //Map used to store RetrofitService
        mServiceMap = new HashMap<>();
        this.context = context;
    }

    @Override
    public void initConfig(NetConfig netConfig) {
        sNetConfig = netConfig;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <S> S getApi(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createApi(serviceClass);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> S getApi(Class<S> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createApi(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    @Override
    public <S> S createApi(Class<S> serviceClass) {
        return createApi(serviceClass, getOkHttpClient());
    }


    @Override
    public <S> S createApi(Class<S> serviceClass, OkHttpClient client) {
        String baseURL = sNetConfig.baseURL;
        if (sNetConfig.isUseMultiBaseURL) {
            try {
                Field field1 = serviceClass.getField("baseURL");
                baseURL = (String) field1.get(serviceClass);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                baseURL = sNetConfig.baseURL;
            } catch (IllegalAccessException e) {
                e.getMessage();
                e.printStackTrace();
                baseURL = sNetConfig.baseURL;
            }
            if (TextUtils.isEmpty(baseURL)) {
                throw new RuntimeException("baseUrl is null .please init by NetModule or apiService field BaseUrl");
            }
        }
        if (sRetrofit != null && sRetrofit.baseUrl().host() == baseURL) {
            return sRetrofit.create(serviceClass);
        } else {
            return getRetrofit(baseURL).create(serviceClass);
        }
    }

    @Override
    public OkHttpClient getClient() {
        if (sOkHttpClient != null) {
            return sOkHttpClient;
        } else {
            return getOkHttpClient();
        }
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }

        return gson;
    }

    /**
     * 如果是需要返回String的数据是使用
     *
     * @return Retrofit
     */
    public Retrofit getRetrofit(String host) {
        if (gson == null) {
            gson = getGson();
        }

        if (sOkHttpClient == null) {
            sOkHttpClient = getOkHttpClient();
        }

        Retrofit.Builder builder = new Retrofit.Builder();
        //baseurl路径
        builder.baseUrl(host);
        //添加客户端
        builder.client(sOkHttpClient)
                .addConverterFactory(new StringConverterFactory()) //添加Gson格式化工厂
                .addConverterFactory(GsonConverterFactory.create(gson)); //添加Gson格式化工厂
            if (sNetConfig.isUseRx) {
                builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());//call 适配器
            }
            sRetrofit = builder.build();
            return sRetrofit;
        }


    public OkHttpClient getOkHttpClient() {
        //对cooke自动管理管理
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        //缓存路径
        File cacheFile = new File(FileUtil.getCacheDirectory(context), "ApiCookie");
        if (!FileUtils.createOrExistsDir(cacheFile)) {
            // 创建文件夹失败
        }

        //设置缓存大小为40M
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 40);
        //缓存
        CacheInterceptor cacheInterceptor = new CacheInterceptor(context);
        //token管理
        TokenInterceptor tokenInterceptor = new TokenInterceptor();
        OkHttpClient.Builder builder =
                new OkHttpClient.Builder()
                        .cache(cache)
                        .addInterceptor(cacheInterceptor)
                        .addInterceptor(tokenInterceptor)
                        .addNetworkInterceptor(cacheInterceptor)
                        //                        .retryOnConnectionFailure(true)
                        .connectTimeout(sNetConfig.connectTimeoutMills != 0 ? sNetConfig.connectTimeoutMills : 15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)//写超时超时
                        .readTimeout(sNetConfig.readTimeoutMills != 0 ? sNetConfig.readTimeoutMills : 15, TimeUnit.SECONDS)//读超时
                        .cookieJar(sNetConfig.mCookieJar != null ? sNetConfig.mCookieJar : cookieJar);

        //如果当前是debug模式就开启日志过滤器
        if (AppUtils.isAppDebug()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置https相关
        if (sNetConfig.mHttpsCall != null) {
            sNetConfig.mHttpsCall.configHttps(builder);
        }
        if (sNetConfig.call != null) {
            builder.addInterceptor(new CallInterceptor(sNetConfig.call));
        }
        if (sNetConfig.mInterceptors != null) {
            for (int i = 0; i < sNetConfig.mInterceptors.length; i++) {
                builder.addInterceptor(sNetConfig.mInterceptors[i]);
            }

        }
        //当前okHttpClient
        sOkHttpClient = builder.build();

        return sOkHttpClient;
    }
}
