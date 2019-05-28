package com.mcgrady.xupdatemanger;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.Map;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateHttpClient {

    void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callback);

    void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callback);

    void download(@NonNull String url, @NonNull String filePath, @NonNull String fileName, @NonNull Callback callback);

    void cancelDownload(@NonNull String url);

    interface Callback {

        void onSuccess(String result);

        void onFailed(Throwable throwable);

        void onFinish();
    }

    interface DownloadCallback {

        void onStart();

        void onProgress();

        void onSuccess(File file);

        void onFailed(Throwable throwable);

        void onFinish();
    }
}
