package com.mcgrady.xproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class GoldManagerBean implements Parcelable {

    private List<GoldManagerItemBean> managerList;

    public GoldManagerBean() {

    }

    public GoldManagerBean(List<GoldManagerItemBean> managerList) {
        this.managerList = managerList;
    }

    public List<GoldManagerItemBean> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<GoldManagerItemBean> managerList) {
        this.managerList = managerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.managerList);
    }

    protected GoldManagerBean(Parcel in) {
        this.managerList = new ArrayList<>();
        in.readList(this.managerList, GoldManagerItemBean.class.getClassLoader());
    }

    public static final Creator<GoldManagerBean> CREATOR = new Creator<GoldManagerBean>() {
        @Override
        public GoldManagerBean createFromParcel(Parcel in) {
            return new GoldManagerBean(in);
        }

        @Override
        public GoldManagerBean[] newArray(int size) {
            return new GoldManagerBean[size];
        }
    };
}
