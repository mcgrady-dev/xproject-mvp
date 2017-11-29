package com.mcgrady.xproject.model;

import com.mcgrady.xproject.model.http.HttpHelper;
import com.mcgrady.xproject.model.prefs.IPreferences;
import com.mcgrady.xproject.model.prefs.PreferencesHelper;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class DataManager implements HttpHelper, PreferencesHelper {

    HttpHelper mHttpHelper;
    PreferencesHelper mPreferencesHelper;

    public DataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
    }
}
