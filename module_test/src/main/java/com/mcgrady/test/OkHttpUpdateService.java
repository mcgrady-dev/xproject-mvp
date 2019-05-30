package com.mcgrady.test;

import android.support.annotation.NonNull;

import com.mcgrady.xupdatemanager.interf.IUpdateDownloadCallback;
import com.mcgrady.xupdatemanager.interf.IUpdateHttpService;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * @author: mcgrady
 * @date: 2019-05-29
 */
public class OkHttpUpdateService implements IUpdateHttpService {

    private OkHttpClient client;

    public OkHttpUpdateService(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callback) {
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callback) {

    }

    @Override
    public void download(@NonNull String url, @NonNull String filePath, @NonNull String fileName, @NonNull IUpdateDownloadCallback callback) {

    }

    @Override
    public void cancelDownload(@NonNull String url) {

    }
}
