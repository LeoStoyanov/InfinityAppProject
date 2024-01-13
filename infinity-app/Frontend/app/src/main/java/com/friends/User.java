package com.friends;

public abstract class User {

    String displayName;
    String username;

    int id;
    int imageId;

    public User(String displayName, String username, int id, int imageId) {
        this.displayName = displayName;
        this.username = username;
        this.id = id;
        this.imageId = imageId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }

}
