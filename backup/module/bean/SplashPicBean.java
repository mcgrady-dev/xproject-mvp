package com.mcgrady.main.module.bean;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/18
 */

public class SplashPicBean {

    /**
     * status : ok
     * time : 1468154610
     * images : ["http://img.owspace.com/Public/uploads/Picture/2016-07-10/578242d91d113.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-10/578242d62ce5f.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-10/578242d2e6e61.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-10/578242d0468b1.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-10/578242cada2b5.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-10/578242c7198d2.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-07/577dbb32d1ca9.jpg","http://img.owspace.com/Public/uploads/Picture/2016-07-05/577b1fcd65e2a.jpg"]
     * count : 8
     */

    private String status;
    private int time;
    private int count;
    private List<String> images;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "SplashPicBean{" +
                "status='" + status + '\'' +
                ", time=" + time +
                ", count=" + count +
                ", images=" + images +
                '}';
    }
}
