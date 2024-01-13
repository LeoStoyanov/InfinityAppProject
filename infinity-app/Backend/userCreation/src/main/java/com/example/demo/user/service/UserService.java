package com.example.demo.user.service;

import com.example.demo.user.model.User;

import java.util.List;

/**
 * Any new methods need to be used by the controller
 * will be first placed here as place holder to be implemented
 * later on
 * @author jroot,ascase
 * 
 */
public interface UserService
{
    /**
     * Posts a user into the database
     * @param user - instance of a new user being inputted
     * @return - the user that was inputted
     */
    User saveUser(User user);

    /**
     * Gets the list of all users in the database
     * @return - a list of all users
     */
    List<User>getAllUsers();

    /**
     * Gets the information of a specific user
     * @param id - the unique id of requested user
     * @return - the specific user needed
     */
    User getUserById(long id);

    /**
     * Updates a users information by creating a new user and overwriting pre-existing information
     * @param user - the new information that needs to be plugged into existing user
     * @param id - id of existing profile that needs updated
     * @return - updated user instance
     */
    User updateUser(User user, long id);

    /**
     * Deletes an instance of one user based on its unique id
     * @param id - id of user being deleted
     */
    void deleteUser(long id);

}
