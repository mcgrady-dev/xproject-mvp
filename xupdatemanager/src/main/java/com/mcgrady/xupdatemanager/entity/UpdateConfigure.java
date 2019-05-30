package com.mcgrady.xupdatemanager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

/**
 * 更新提示参数信息
 *
 * @author: mcgrady
 * @date: 2019-05-28
 */
public class UpdateConfigure implements Parcelable {

    /**
     * 主题颜色
     */
    @ColorInt
    private int themeColor;

    /**
     * 顶部背景图片
     */
    @DrawableRes
    private int topResId;

    /**
     * 是否支持后台更新
     */
    private boolean isBackgroundUpdate;

    public UpdateConfigure() {
        themeColor = -1;
        topResId = -1;
        isBackgroundUpdate = false;
    }

    protected UpdateConfigure(Parcel in) {
        themeColor = in.readInt();
        topResId = in.readInt();
        isBackgroundUpdate = in.readByte() != 0;
    }

    public static final Creator<UpdateConfigure> CREATOR = new Creator<UpdateConfigure>() {
        @Override
        public UpdateConfigure createFromParcel(Parcel in) {
            return new UpdateConfigure(in);
        }

        @Override
        public UpdateConfigure[] newArray(int size) {
            return new UpdateConfigure[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(themeColor);
        dest.writeInt(topResId);
        dest.writeByte((byte) (isBackgroundUpdate ? 1 : 0));
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }

    public int getTopResId() {
        return topResId;
    }

    public void setTopResId(int topResId) {
        this.topResId = topResId;
    }

    public boolean isBackgroundUpdate() {
        return isBackgroundUpdate;
    }

    public void setBackgroundUpdate(boolean backgroundUpdate) {
        isBackgroundUpdate = backgroundUpdate;
    }

    @Override
    public String toString() {
        return "UpdateConfigure{" +
                "themeColor=" + themeColor +
                ", topResId=" + topResId +
                ", isBackgroundUpdate=" + isBackgroundUpdate +
                '}';
    }
}
