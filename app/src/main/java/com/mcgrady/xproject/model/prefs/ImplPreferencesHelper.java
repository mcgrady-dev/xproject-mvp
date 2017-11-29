package com.mcgrady.xproject.model.prefs;

import com.blankj.utilcode.util.SPUtils;
import com.mcgrady.xproject.app.Constants;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/11/29.
 */

public class ImplPreferencesHelper implements PreferencesHelper {

    private static final boolean DEFAULT_NIGHT_MODE = false;
    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final boolean DEFAULT_AUTO_SAVE = true;

    private static final boolean DEFAULT_LIKE_POINT = false;
    private static final boolean DEFAULT_VERSION_POINT = false;
    private static final boolean DEFAULT_MANAGER_POINT = false;

    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_ZHIHU;

    private static final String SHAREDPREFERENCES_NAME = "xpoject_sp";

    private final SPUtils spUtils;

    @Inject
    public ImplPreferencesHelper() {
        spUtils = SPUtils.getInstance(SHAREDPREFERENCES_NAME);
    }

    @Override
    public boolean getNightModeState() {
        return spUtils.getBoolean(Constants.SP_NIGHT_MODE, DEFAULT_NIGHT_MODE);
    }

    @Override
    public void setNightModeState(boolean state) {
        spUtils.put(Constants.SP_NIGHT_MODE, state);
    }

    @Override
    public boolean getNoImageState() {
        return spUtils.getBoolean(Constants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    @Override
    public void setNoImageState(boolean state) {
        spUtils.put(Constants.SP_NO_IMAGE, state);
    }

    @Override
    public boolean getAutoCacheState() {
        return spUtils.getBoolean(Constants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    @Override
    public void setAutoCacheState(boolean state) {
        spUtils.put(Constants.SP_AUTO_CACHE, state);
    }

    @Override
    public int getCurrentItem() {
        return spUtils.getInt(Constants.SP_CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
    }

    @Override
    public void setCurrentItem(int item) {
        spUtils.put(Constants.SP_CURRENT_ITEM, item);
    }

    @Override
    public boolean getLikePoint() {
        return spUtils.getBoolean(Constants.SP_LIKE_POINT, DEFAULT_LIKE_POINT);
    }

    @Override
    public void setLikePoint(boolean isFirst) {
        spUtils.put(Constants.SP_LIKE_POINT, isFirst);
    }

    @Override
    public boolean getVersionPoint() {
        return spUtils.getBoolean(Constants.SP_VERSION_POINT, DEFAULT_VERSION_POINT);
    }

    @Override
    public void setVersionPoint(boolean isFirst) {
        spUtils.put(Constants.SP_VERSION_POINT, isFirst);
    }

    @Override
    public boolean getManagerPoint() {
        return spUtils.getBoolean(Constants.SP_MANAGER_POINT, DEFAULT_MANAGER_POINT);
    }

    @Override
    public void setManagerPoint(boolean isFirst) {
        spUtils.put(Constants.SP_MANAGER_POINT, isFirst);
    }
}
