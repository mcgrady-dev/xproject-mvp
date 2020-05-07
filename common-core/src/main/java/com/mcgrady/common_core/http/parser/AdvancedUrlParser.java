package com.mcgrady.common_core.http.parser;

import android.text.TextUtils;

import com.mcgrady.common_core.http.manager.RetrofitUrlManager;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.LruCache;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * Created by mcgrady on 2020/4/29.
 */
class AdvancedUrlParser implements UrlParser {
    private RetrofitUrlManager mRetrofitUrlManager;
    private Cache<String, String> mCache;

    @Override
    public void init(RetrofitUrlManager retrofitUrlManager) {
        this.mRetrofitUrlManager = retrofitUrlManager;
        this.mCache = new LruCache<>(100);
    }

    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;

        HttpUrl.Builder builder = url.newBuilder();

        if (TextUtils.isEmpty(mCache.get(getKey(domainUrl, url)))) {
            for (int i = 0; i < url.pathSize(); i++) {
                //当删除了上一个 index, PathSegment 的 item 会自动前进一位, 所以 remove(0) 就好
                builder.removePathSegment(0);
            }

            List<String> newPathSegments = new ArrayList<>();
            newPathSegments.addAll(domainUrl.encodedPathSegments());

            if (url.pathSize() > mRetrofitUrlManager.getPathSize()) {
                List<String> encodedPathSegments = url.encodedPathSegments();
                for (int i = mRetrofitUrlManager.getPathSize(); i < encodedPathSegments.size(); i++) {
                    newPathSegments.add(encodedPathSegments.get(i));
                }
            } else if (url.pathSize() < mRetrofitUrlManager.getPathSize()) {
                throw new IllegalArgumentException(String.format("Your final path is %s, but the baseUrl of your RetrofitUrlManager#startAdvancedModel is %s",
                        url.scheme() + "://" + url.host() + url.encodedPath(),
                        mRetrofitUrlManager.getBaseUrl().scheme() + "://"
                                + mRetrofitUrlManager.getBaseUrl().host()
                                + mRetrofitUrlManager.getBaseUrl().encodedPath()));
            }

            for (String PathSegment : newPathSegments) {
                builder.addEncodedPathSegment(PathSegment);
            }
        } else {
            builder.encodedPath(mCache.get(getKey(domainUrl, url)));
        }

        HttpUrl httpUrl = builder
                .scheme(domainUrl.scheme())
                .host(domainUrl.host())
                .port(domainUrl.port())
                .build();

        if (TextUtils.isEmpty(mCache.get(getKey(domainUrl, url)))) {
            mCache.put(getKey(domainUrl, url), httpUrl.encodedPath());
        }
        return httpUrl;
    }

    private String getKey(HttpUrl domainUrl, HttpUrl url) {
        return domainUrl.encodedPath() + url.encodedPath()
                + mRetrofitUrlManager.getPathSize();
    }
}
