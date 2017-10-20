package com.mcgrady.xproject.model;

import java.util.List;

/**
 * Created by mcgrady on 2017/10/18.
 */

public class AllCity {

    private int error_code;
    private String reason;
    private List<City> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<City> getResult() {
        return result;
    }

    public void setResult(List<City> result) {
        this.result = result;
    }

    public static class City {

        private String id;
        private String city;
        private String district;
        private String province;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}
