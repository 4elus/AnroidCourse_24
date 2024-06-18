package com.alisherzhanibek.newnewfirebase;

public class User {
    private String name;
    private String email;
    private String password;
    private String id;
    private int avatarUser;
    public User() {
    }
    public User(String name, String email, String password, String id, int avatarUser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.avatarUser = avatarUser;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAvatarUser() {
        return avatarUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatarUser(int avatarUser) {
        this.avatarUser = avatarUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
