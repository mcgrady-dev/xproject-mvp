package com.mcgrady.xskeleton.module;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.http.BaseUrl;
import com.mcgrady.xskeleton.http.GlobalHttpHandler;
import com.mcgrady.xskeleton.http.imageloader.BaseImageLoaderStrategy;
import com.mcgrady.xskeleton.http.interf.ResponseErrorListener;
import com.mcgrady.xskeleton.http.log.FormatPrinter;
import com.mcgrady.xskeleton.http.log.RequestInterceptor;
import com.mcgrady.xskeleton.integration.IRepositoryManager;
import com.mcgrady.xskeleton.utils.Preconditions;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by mcgrady on 2020/4/3.
 */
public class GlobalConfigModule {

    private Application application;
    //network
    private HttpUrl apiUrl;
    private BaseUrl baseUrl;
    private Gson gson;
    private ClientModule.RetrofitConfiguration retrofitConfig;
    private ClientModule.OkhttpConfiguration okhttpConfig;
    private AppModule.GsonConfiguration gsonConfig;
    private RequestInterceptor.Level printHttpLogLevel;
    private FormatPrinter formatPrinter;
    private List<Interceptor> interceptors;
    private ResponseErrorListener errorListener;
    private GlobalHttpHandler httpHandler;
    private IRepositoryManager.ObtainServiceDelegate obtainServiceDelegate;
    private BaseImageLoaderStrategy imageLoaderStrategy;

    //cache
    private File cacheFile;
    private Cache.Factory cacheFactory;

    private ExecutorService executorService;

    public Application getApplication() {
        return application;
    }

    public HttpUrl getApiUrl() {
        return apiUrl;
    }

    public BaseUrl getBaseUrl() {
        return baseUrl;
    }

    public Gson getGson() {
        return gson;
    }

    public ClientModule.RetrofitConfiguration getRetrofitConfig() {
        return retrofitConfig;
    }

    public ClientModule.OkhttpConfiguration getOkhttpConfig() {
        return okhttpConfig;
    }

    public AppModule.GsonConfiguration getGsonConfig() {
        return gsonConfig;
    }

    public RequestInterceptor.Level getPrintHttpLogLevel() {
        return printHttpLogLevel;
    }

    public FormatPrinter getFormatPrinter() {
        return formatPrinter;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public ResponseErrorListener getErrorListener() {
        return errorListener;
    }

    public GlobalHttpHandler getHttpHandler() {
        return httpHandler;
    }

    public IRepositoryManager.ObtainServiceDelegate getObtainServiceDelegate() {
        return obtainServiceDelegate;
    }

    public File getCacheFile() {
        return cacheFile;
    }

    public Cache.Factory getCacheFactory() {
        return cacheFactory;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public BaseImageLoaderStrategy getImageLoaderStrategy() {
        return imageLoaderStrategy;
    }

    private GlobalConfigModule(Builder builder) {
        this.apiUrl = builder.apiUrl;
        this.baseUrl = builder.baseUrl;
        this.okhttpConfig = builder.okhttpConfig;
        this.retrofitConfig = builder.retrofitConfig;
        this.errorListener = builder.errorListener;
        this.gsonConfig = builder.gsonConfig;
        this.printHttpLogLevel = builder.printHttpLogLevel;
        this.formatPrinter = builder.formatPrinter;
        this.interceptors = builder.interceptors;
        this.httpHandler = builder.httpHandler;
        this.obtainServiceDelegate = builder.obtainServiceDelegate;
        this.imageLoaderStrategy = builder.imageLoaderStrategy;

        this.cacheFile = builder.cacheFile;
        this.cacheFactory = builder.cacheFactory;
        this.executorService = builder.executorService;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private ClientModule.OkhttpConfiguration okhttpConfig;
        private ClientModule.RetrofitConfiguration retrofitConfig;
        private IRepositoryManager.ObtainServiceDelegate obtainServiceDelegate;
        private AppModule.GsonConfiguration gsonConfig;
        private RequestInterceptor.Level printHttpLogLevel;
        private FormatPrinter formatPrinter;
        private List<Interceptor> interceptors;
        private ResponseErrorListener errorListener;
        private GlobalHttpHandler httpHandler;
        private BaseImageLoaderStrategy imageLoaderStrategy;

        private File cacheFile;
        private Cache.Factory cacheFactory;

        private ExecutorService executorService;

        private Builder() {
        }

        public Builder baseUrl(String baseUrl) {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(BaseUrl baseUrl) {
            this.baseUrl = Preconditions.checkNotNull(baseUrl, BaseUrl.class.getCanonicalName() + "can not be null");
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder globalHttpHandler(GlobalHttpHandler handler) {
            this.httpHandler = handler;
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener listener) {
            this.errorListener = listener;
            return this;
        }

        public Builder retrofitConfiguration(ClientModule.RetrofitConfiguration retrofitConfig) {
            this.retrofitConfig = retrofitConfig;
            return this;
        }

        public Builder okhttpConfiguration(ClientModule.OkhttpConfiguration okhttpConfig) {
            this.okhttpConfig = okhttpConfig;
            return this;
        }

        public Builder gsonConfiguration(AppModule.GsonConfiguration gsonConfig) {
            this.gsonConfig = gsonConfig;
            return this;
        }

        public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) {
            this.printHttpLogLevel = printHttpLogLevel;
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter) {
            this.formatPrinter = formatPrinter;
            return this;
        }

        public Builder executorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder obtainServiceDelegate(IRepositoryManager.ObtainServiceDelegate obtainServiceDelegate) {
            this.obtainServiceDelegate = obtainServiceDelegate;
            return this;
        }

        public Builder cacheFactory(Cache.Factory cacheFactory) {
            this.cacheFactory = cacheFactory;
            return this;
        }

        public Builder imageLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {
            this.imageLoaderStrategy = loaderStrategy;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
