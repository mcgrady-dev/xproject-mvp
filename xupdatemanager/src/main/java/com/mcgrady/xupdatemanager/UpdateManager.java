package com.mcgrady.xupdatemanager;

import android.app.Application;

import com.mcgrady.xupdatemanager.interf.IUpdateChecker;
import com.mcgrady.xupdatemanager.interf.IUpdateDownloadCallback;
import com.mcgrady.xupdatemanager.interf.IUpdateDownloader;
import com.mcgrady.xupdatemanager.interf.IUpdateHttpService;
import com.mcgrady.xupdatemanager.interf.IUpdateParser;
import com.mcgrady.xupdatemanager.interf.IUpdatePrompter;
import com.mcgrady.xupdatemanager.entity.UpdateConfigure;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author: mcgrady
 * @date: 2019-05-28
 */
public class UpdateManager {

    private static UpdateManager sInstance = null;

    private Application context;

    private Map<String, Object> params;

    /**
     * 请求类型：0GET 1POST，默认POST
     */
    private int requestType;

    private boolean isWifiOnly;

    private boolean isAutoMode;

    private String ApkCacheDir;

    private IUpdateHttpService updateHttpClient;

    private IUpdateChecker updateChecker;

    private IUpdateParser updateParser;

    private IUpdateDownloader updateDownloader;

    private IUpdateDownloadCallback updateDownloadListener;

    private IUpdatePrompter updatePrompter;

    private UpdateConfigure updateConfigure;


    private UpdateManager() {
        requestType = 1;
        isWifiOnly = true;
        isAutoMode = false;


    }

    public static UpdateManager getInstance() {
        if (sInstance == null) {
            synchronized (UpdateManager.class) {
                if (sInstance == null) {
                    sInstance = new UpdateManager();
                }
            }
        }

        return sInstance;
    }

    public void init(Application application) {
        this.context = application;
    }


    public static final class Builder {
        private Application context;
        private Map<String, Object> params;
        private int requestType;
        private boolean isWifiOnly;
        private boolean isAutoMode;
        private String ApkCacheDir;
        private IUpdateHttpService updateHttpClient;
        private IUpdateChecker updateChecker;
        private IUpdateParser updateParser;
        private IUpdateDownloader updateDownloader;
        private IUpdateDownloadCallback updateDownloadListener;
        private IUpdatePrompter updatePrompter;
        private UpdateConfigure updateConfigure;

        private Builder() {
            params = new HashMap<>();
        }

        public static Builder anUpdateManager() {
            return new Builder();
        }

        public Builder context(Application context) {
            this.context = context;
            return this;
        }

        public Builder params(Map<String, Object> params) {
            this.params.putAll(params);
            return this;
        }

        public Builder params(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder requestType(int requestType) {
            this.requestType = requestType;
            return this;
        }

        public Builder isWifiOnly(boolean isWifiOnly) {
            this.isWifiOnly = isWifiOnly;
            return this;
        }

        public Builder isAutoMode(boolean isAutoMode) {
            this.isAutoMode = isAutoMode;
            return this;
        }

        public Builder setApkCacheDir(String ApkCacheDir) {
            this.ApkCacheDir = ApkCacheDir;
            return this;
        }

        public Builder updateHttpClient(IUpdateHttpService updateHttpClient) {
            this.updateHttpClient = updateHttpClient;
            return this;
        }

        public Builder updateChecker(IUpdateChecker updateChecker) {
            this.updateChecker = updateChecker;
            return this;
        }

        public Builder updateParser(IUpdateParser updateParser) {
            this.updateParser = updateParser;
            return this;
        }

        public Builder updateDownloader(IUpdateDownloader updateDownloader) {
            this.updateDownloader = updateDownloader;
            return this;
        }

        public Builder updateDownloadListener(IUpdateDownloadCallback updateDownloadListener) {
            this.updateDownloadListener = updateDownloadListener;
            return this;
        }

        public Builder updatePrompter(IUpdatePrompter updatePrompter) {
            this.updatePrompter = updatePrompter;
            return this;
        }

        public Builder updateConfigure(UpdateConfigure updateConfigure) {
            this.updateConfigure = updateConfigure;
            return this;
        }

        public UpdateManager build() {
            UpdateManager updateManager = new UpdateManager();
            updateManager.ApkCacheDir = this.ApkCacheDir;
            updateManager.isAutoMode = this.isAutoMode;
            updateManager.updateHttpClient = this.updateHttpClient;
            updateManager.requestType = this.requestType;
            updateManager.updateDownloader = this.updateDownloader;
            updateManager.context = this.context;
            updateManager.isWifiOnly = this.isWifiOnly;
            updateManager.params = this.params;
            updateManager.updateParser = this.updateParser;
            updateManager.updateConfigure = this.updateConfigure;
            updateManager.updateChecker = this.updateChecker;
            updateManager.updatePrompter = this.updatePrompter;
            updateManager.updateDownloadListener = this.updateDownloadListener;
            return updateManager;
        }
    }
}
