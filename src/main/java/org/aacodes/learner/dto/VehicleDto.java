package org.aacodes.learner.dto;

import org.apache.solr.client.solrj.beans.Field;

public class VehicleDto {

    @Field("id")
    private String id;

    @Field("type")
    private String type;

    @Field("brand")
    private String brand;

    @Field("ckcount")
    private int ckcount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getCkcount() {
        return ckcount;
    }

    public void setCkcount(int ckcount) {
        this.ckcount = ckcount;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", ckcount=" + ckcount +
                '}';
    }
}
