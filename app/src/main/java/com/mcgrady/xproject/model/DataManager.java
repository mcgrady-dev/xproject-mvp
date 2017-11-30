package com.mcgrady.xproject.model;

import com.mcgrady.xproject.model.bean.GoldListBean;
import com.mcgrady.xproject.model.http.HttpHelper;
import com.mcgrady.xproject.model.http.response.GoldHttpResponse;
import com.mcgrady.xproject.model.prefs.PreferencesHelper;

import java.util.List;

import io.reactivex.Flowable;

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

    @Override
    public boolean getNightModeState() {
        return mPreferencesHelper.getNightModeState();
    }

    @Override
    public void setNightModeState(boolean state) {
        mPreferencesHelper.setNightModeState(state);
    }

    @Override
    public boolean getNoImageState() {
        return mPreferencesHelper.getNoImageState();
    }

    @Override
    public void setNoImageState(boolean state) {
        mPreferencesHelper.setNoImageState(state);
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferencesHelper.getAutoCacheState();
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mPreferencesHelper.setAutoCacheState(state);
    }

    @Override
    public int getCurrentItem() {
        return mPreferencesHelper.getCurrentItem();
    }

    @Override
    public void setCurrentItem(int item) {
        mPreferencesHelper.setCurrentItem(item);
    }

    @Override
    public boolean getLikePoint() {
        return mPreferencesHelper.getLikePoint();
    }

    @Override
    public void setLikePoint(boolean isFirst) {
        mPreferencesHelper.setLikePoint(isFirst);
    }

    @Override
    public boolean getVersionPoint() {
        return mPreferencesHelper.getVersionPoint();
    }

    @Override
    public void setVersionPoint(boolean isFirst) {
        mPreferencesHelper.setVersionPoint(isFirst);
    }

    @Override
    public boolean getManagerPoint() {
        return mPreferencesHelper.getManagerPoint();
    }

    @Override
    public void setManagerPoint(boolean isFirst) {
        mPreferencesHelper.setManagerPoint(isFirst);
    }

    @Override
    public Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit) {
        return mHttpHelper.fetchGoldHotList(type, dataTime, limit);
    }

    @Override
    public Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page) {
        return mHttpHelper.fetchGoldList(type, num, page);
    }
}
