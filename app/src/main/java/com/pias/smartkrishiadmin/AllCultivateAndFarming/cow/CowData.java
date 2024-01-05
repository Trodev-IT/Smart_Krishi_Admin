package com.pias.smartkrishiadmin.AllCultivateAndFarming.cow;

public class CowData {
    String name, pdf, image, key;

    public CowData() {
    }

    public CowData(String name, String pdf, String image, String key) {
        this.name = name;
        this.pdf = pdf;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
