package com.mcgrady.xupdatemanager.interf;

import androidx.annotation.NonNull;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateDownloader<T> {

    void startDownload(@NonNull T updateEntity, @NonNull IUpdateDownloadCallback listener);

    void cancelDownload();

    void backgroundDownload();
}
