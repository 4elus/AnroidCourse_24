package com.example.materialdesign;

public class MyItem {
    private String title;
    private int img;
    private int price;
    private String desc;

    public MyItem(String title, int img, int price, String desc) {
        this.title = title;
        this.img = img;
        this.price = price;
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public int getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }
}
