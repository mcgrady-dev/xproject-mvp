package com.mcgrady.xupdatemanager.interf;

import androidx.annotation.NonNull;

import java.util.Map;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateHttpService {

    void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callback);

    void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callback);

    void download(@NonNull String url, @NonNull String filePath, @NonNull String fileName, @NonNull IUpdateDownloadCallback callback);

    void cancelDownload(@NonNull String url);

    interface Callback {

        void onSuccess(String result);

        void onFailed(Throwable throwable);

        void onFinish();
    }
}
