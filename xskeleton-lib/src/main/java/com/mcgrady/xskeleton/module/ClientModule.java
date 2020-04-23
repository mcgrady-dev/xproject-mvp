package com.mcgrady.xskeleton.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.mcgrady.xskeleton.http.GlobalHttpHandler;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.http.interf.ResponseErrorListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mcgrady on 2020/4/3.
 */
public class ClientModule {
    private static final int TIME_OUT = 10;

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private RxErrorHandler rxErrorHandler;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public RxErrorHandler getRxErrorHandler() {
        return rxErrorHandler;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private Application application;
        private HttpUrl httpUrl;
        private Gson gson;
        private RetrofitConfiguration retrofitConfig;
        private OkhttpConfiguration okhttpClientConfig;
        private Interceptor intercept;
        private List<Interceptor> interceptors;
        private GlobalHttpHandler globalHttpHandler;
        private ExecutorService executorService;
        private ResponseErrorListener responseErrorListener;

        private Builder() {
        }

        public Builder with(@NonNull Application application) {
            this.application = application;
            return this;
        }

        public Builder retrofitConfig(@NonNull RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfig = retrofitConfiguration;
            return this;
        }

        public Builder okhttpClientConfig(@NonNull OkhttpConfiguration okhttpConfiguration) {
            this.okhttpClientConfig = okhttpConfiguration;
            return this;
        }

        public Builder httpUrl(@NonNull HttpUrl httpUrl) {
            this.httpUrl = httpUrl;
            return this;
        }

        public Builder gson(@NonNull Gson gson) {
            this.gson = gson;
            return this;
        }

        public Builder intercept(@NonNull Interceptor intercept) {
            this.intercept = intercept;
            return this;
        }

        public Builder interceptors(@NonNull List<Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder globalHttpHandler(@NonNull GlobalHttpHandler globalHttpHandler) {
            this.globalHttpHandler = globalHttpHandler;
            return this;
        }

        public Builder executorService(@NonNull ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }

        public ClientModule build() {
            ClientModule clientModule = new ClientModule();

            //OkHttpClient
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            clientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS);

            if (intercept != null) {
                clientBuilder.addNetworkInterceptor(intercept);
            }

            if (globalHttpHandler != null) {
                clientBuilder.addInterceptor(chain -> chain.proceed(globalHttpHandler.onHttpRequestBefore(chain, chain.request())));
            }

            //如果外部提供了 Interceptor 的集合则遍历添加
            if (interceptors != null && !interceptors.isEmpty()) {
                for (Interceptor interceptor : interceptors) {
                    clientBuilder.addInterceptor(interceptor);
                }
            }

            //为 OkHttp 设置默认的线程池
            clientBuilder.dispatcher(new Dispatcher(executorService));

            if (okhttpClientConfig != null) {
                okhttpClientConfig.configOkhttp(application, clientBuilder);
            }
            clientModule.okHttpClient = clientBuilder.build();

            //Retrofit
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(httpUrl)
                    .client(clientModule.okHttpClient);

            if (this.retrofitConfig != null) {
                this.retrofitConfig.configRetrofit(this.application, retrofitBuilder);
            }
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(this.gson));
            clientModule.retrofit = retrofitBuilder.build();

            //RxErrorListener
            clientModule.rxErrorHandler = RxErrorHandler.builder()
                    .with(application)
                    .responseErrorListener(responseErrorListener)
                    .create();

            return clientModule;
        }
    }

    public interface RetrofitConfiguration {
        void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder);
    }
}
