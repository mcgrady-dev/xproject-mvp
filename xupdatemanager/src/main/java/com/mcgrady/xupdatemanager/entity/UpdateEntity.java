package com.mcgrady.xupdatemanager.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.mcgrady.xupdatemanager.interf.IUpdateHttpService;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public class UpdateEntity implements Parcelable {

    private boolean hasNewVersion;
    private boolean isForce;
    private boolean ignore;

    private int versionCode;
    private String versionName;
    private String updateContent;
    private UpdateDownloadEntity downloadEntity;

    private boolean isSilent;
    private boolean isAutoInstall;

    private IUpdateHttpService updateHttpClient;

    public IUpdateHttpService getUpdateHttpClient() {
        return updateHttpClient;
    }

    public void setUpdateHttpClient(IUpdateHttpService updateHttpClient) {
        this.updateHttpClient = updateHttpClient;
    }

    public boolean isHasNewVersion() {
        return hasNewVersion;
    }

    public void setHasNewVersion(boolean hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public UpdateDownloadEntity getDownloadEntity() {
        return downloadEntity;
    }

    public void setDownloadEntity(UpdateDownloadEntity downloadEntity) {
        this.downloadEntity = downloadEntity;
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public boolean isAutoInstall() {
        return isAutoInstall;
    }

    public void setAutoInstall(boolean autoInstall) {
        isAutoInstall = autoInstall;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.hasNewVersion ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isForce ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ignore ? (byte) 1 : (byte) 0);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeString(this.updateContent);
        dest.writeParcelable(this.downloadEntity, flags);
        dest.writeByte(this.isSilent ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAutoInstall ? (byte) 1 : (byte) 0);
    }

    public UpdateEntity() {
    }

    protected UpdateEntity(Parcel in) {
        this.hasNewVersion = in.readByte() != 0;
        this.isForce = in.readByte() != 0;
        this.ignore = in.readByte() != 0;
        this.versionCode = in.readInt();
        this.versionName = in.readString();
        this.updateContent = in.readString();
        this.downloadEntity = in.readParcelable(UpdateDownloadEntity.class.getClassLoader());
        this.isSilent = in.readByte() != 0;
        this.isAutoInstall = in.readByte() != 0;
    }

    public static final Parcelable.Creator<UpdateEntity> CREATOR = new Parcelable.Creator<UpdateEntity>() {
        @Override
        public UpdateEntity createFromParcel(Parcel source) {
            return new UpdateEntity(source);
        }

        @Override
        public UpdateEntity[] newArray(int size) {
            return new UpdateEntity[size];
        }
    };

    @Override
    public String toString() {
        return "UpdateEntity{" +
                "hasNewVersion=" + hasNewVersion +
                ", isForce=" + isForce +
                ", ignore=" + ignore +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", downloadEntity=" + downloadEntity +
                ", isSilent=" + isSilent +
                ", isAutoInstall=" + isAutoInstall +
                '}';
    }
}
