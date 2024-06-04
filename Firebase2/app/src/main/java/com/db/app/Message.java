package com.db.app;

public class Message {
    private String text;
    private String name;
    private String imageurl;

    private Message(){}

    public Message(String text, String name, String imageurl) {
        this.text = text;
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}

