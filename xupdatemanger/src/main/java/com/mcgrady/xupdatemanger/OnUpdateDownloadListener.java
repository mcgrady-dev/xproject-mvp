package com.mcgrady.xupdatemanger;

import java.io.File;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface OnUpdateDownloadListener {

    void onStart();

    void onProgress(float progress, long total);

    boolean onCompleted(File file);

    void onError(Throwable throwable);
}
