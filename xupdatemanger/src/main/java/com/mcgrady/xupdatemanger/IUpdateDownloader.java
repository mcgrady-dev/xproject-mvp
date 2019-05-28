package com.mcgrady.xupdatemanger;

import android.support.annotation.NonNull;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateDownloader<T> {

    void startDownload(@NonNull T updateEntity, @NonNull OnUpdateDownloadListener listener);

    void cancelDownload();

    void backgroundDownload();
}
