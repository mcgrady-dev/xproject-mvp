package com.mcgrady.xproject.model;

import com.mcgrady.xproject.model.http.HttpHelper;
import com.mcgrady.xproject.model.prefs.IPreferences;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;
    IPreferences mPreferencesHelper;

    public DataManager(HttpHelper httpHelper, IPreferences preferencesHelper) {
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
    }
}
