package org.aacodes.learner.model;

import org.apache.solr.client.solrj.beans.Field;

public class VehicleModel {

    @Field("id")
    private String id;

    @Field("type")
    private String type;

    @Field("brand")
    private String brand;

    @Field("ckcount")
    private int ckcount;

    @Field("score")
    private double score;

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "VehicleModel{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", ckcount=" + ckcount +
                ", score=" + score +
                '}';
    }

}
