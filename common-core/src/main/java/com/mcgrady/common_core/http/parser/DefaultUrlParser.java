package com.mcgrady.common_core.http.parser;

import com.mcgrady.common_core.http.manager.RetrofitUrlManager;

import okhttp3.HttpUrl;

import static com.mcgrady.common_core.http.manager.RetrofitUrlManager.IDENTIFICATION_PATH_SIZE;

/**
 * Created by mcgrady on 2020/4/28.
 */
public class DefaultUrlParser implements UrlParser {
    private UrlParser mDomainUrlParser;
    private volatile UrlParser mAdvancedUrlParser;
    private volatile UrlParser mSuperUrlParser;
    private RetrofitUrlManager mRetrofitUrlManager;

    @Override
    public void init(RetrofitUrlManager retrofitUrlManager) {
        this.mRetrofitUrlManager = retrofitUrlManager;
        this.mDomainUrlParser = new DomainUrlParser();
        this.mDomainUrlParser.init(retrofitUrlManager);
    }

    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;

        if (url.toString().contains(IDENTIFICATION_PATH_SIZE)) {
            if (mSuperUrlParser == null) {
                synchronized (this) {
                    if (mSuperUrlParser == null) {
                        mSuperUrlParser = new SuperUrlParser();
                        mSuperUrlParser.init(mRetrofitUrlManager);
                    }
                }
            }
            return mSuperUrlParser.parseUrl(domainUrl, url);
        }

        //如果是高级模式则使用高级解析器
        if (mRetrofitUrlManager.isAdvancedModel()) {
            if (mAdvancedUrlParser == null) {
                synchronized (this) {
                    if (mAdvancedUrlParser == null) {
                        mAdvancedUrlParser = new AdvancedUrlParser();
                        mAdvancedUrlParser.init(mRetrofitUrlManager);
                    }
                }
            }
            return mAdvancedUrlParser.parseUrl(domainUrl, url);
        }
        return mDomainUrlParser.parseUrl(domainUrl, url);
    }
}
