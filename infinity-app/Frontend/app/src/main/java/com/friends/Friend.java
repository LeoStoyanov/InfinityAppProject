package com.friends;
/**
 * @author Leo Stoyanov
 */
/**
 * Class contains the creation of a Friend object and its attributes.
 */
public class Friend extends User {
    /**
     * Constructor to instantiate a Friend object containing attributes, such as the user's
     * display name, username, id, and image ID.
     * @param displayName user's display name
     * @param username user's username
     * @param id user's assigned ID
     * @param imageId user's image ID
     */
    public Friend(String displayName, String username, int id, int imageId) {
        super(displayName, username, id, imageId);
    }
}