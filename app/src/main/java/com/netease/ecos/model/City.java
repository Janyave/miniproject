package com.netease.ecos.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Title: City.java 
 * @Description: 城市
 * @author enlizhang   
 * @date 2015年7月25日 下午11:26:18 
 */

@DatabaseTable(tableName = "city_info")
public class City {

    // 主键 id
    @DatabaseField(id = true)
    public String id;

    @DatabaseField
    public String cityName;

    @DatabaseField
    public String cityCode;

    @DatabaseField(canBeNull = false)
    public String provinceId;

    public City()
    {

    }

    public String getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}

