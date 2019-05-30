package com.mcgrady.xupdatemanager.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public class UpdateDownloadEntity implements Parcelable {

    private String downloadUrl;
    private String cacheDir;
    private String md5;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }

    private long fileSize;
    private boolean isNotification;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.downloadUrl);
        dest.writeString(this.cacheDir);
        dest.writeString(this.md5);
        dest.writeLong(this.fileSize);
        dest.writeByte(this.isNotification ? (byte) 1 : (byte) 0);
    }

    public UpdateDownloadEntity() {
    }

    protected UpdateDownloadEntity(Parcel in) {
        this.downloadUrl = in.readString();
        this.cacheDir = in.readString();
        this.md5 = in.readString();
        this.fileSize = in.readLong();
        this.isNotification = in.readByte() != 0;
    }

    public static final Parcelable.Creator<UpdateDownloadEntity> CREATOR = new Parcelable.Creator<UpdateDownloadEntity>() {
        @Override
        public UpdateDownloadEntity createFromParcel(Parcel source) {
            return new UpdateDownloadEntity(source);
        }

        @Override
        public UpdateDownloadEntity[] newArray(int size) {
            return new UpdateDownloadEntity[size];
        }
    };

    @Override
    public String toString() {
        return "UpdateDownloadEntity{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", cacheDir='" + cacheDir + '\'' +
                ", md5='" + md5 + '\'' +
                ", fileSize=" + fileSize +
                ", isNotification=" + isNotification +
                '}';
    }
}
