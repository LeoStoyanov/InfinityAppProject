package com.example.demo.user;


import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author ascase, Jroot
 */
public interface UserService
{
    /**
     * gets all of the users in the database
     * @return all users in the database
     */
    public List<User> getAllUsers();

    /**
     * Gets the user based on their id
     * @param id - unique id of the user
     * @return - the user based on the id given
     */
    public User getUserById(long id);

    /**
     * take an new user object then saves it into the database
     * @param user - the instance of a new user
     * @return - An HTTP status
     */
    public User saveUser(User user);

    /**
     * Updates an existing user with new information
     * @param id - id of existing user
     * @param user - All of the information we are updating the existing user with
     * @return - HTTP status
     */
    public User updateUser (long id, User user);

    /**
     * Deletes an existing user
     * @param id  - unique id of user we are deleting
     * @return - String of completion, as well as HTTP Status
     */
    public String deleteUser(long id);
    
    public Integer CountOfUser();

}
