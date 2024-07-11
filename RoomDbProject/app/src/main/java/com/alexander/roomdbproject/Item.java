package com.alexander.roomdbproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = DbConfig.ITEM_TABLE)
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "title")
    public String title;


    @ColumnInfo(name = "description")
    public String description;


    @ColumnInfo(name = "price")
    public int price;


    @ColumnInfo(name = "image")
    public int image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
