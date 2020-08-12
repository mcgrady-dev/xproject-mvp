package com.mcgrady.shop.mvp.model.bean;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class SkuAttribute {

    private String key;
    private String value;

    public SkuAttribute() {
    }

    public SkuAttribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SkuAttribute{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
