package com.friends;

public class NonFriend extends User {
    /**
     * Constructor to instantiate a User object containing attributes, such as the user's
     * display name, username, id, and image ID. Multiple User objects are created once
     * the user logs in.
     *
     * @param displayName user's display name
     * @param username    user's username
     * @param id          user's assigned ID
     * @param imageId     user's image ID
     */
    public NonFriend(String displayName, String username, int id, int imageId) {
        super(displayName, username, id, imageId);
    }
}
