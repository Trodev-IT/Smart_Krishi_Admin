package com.pias.smartkrishiadmin.activity;

public class AgricultureData {
    private String name , roll , result, father,  image , key;

    public AgricultureData() {
    }

    public AgricultureData(String name, String roll, String result, String father, String image, String key) {
        this.name = name;
        this.roll = roll;
        this.result = result;
        this.father = father;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
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
