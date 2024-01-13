package com.example.demo.user.controller;

import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class handles all of the client to backend requests
 * all of these classes hand all requests off to the Service
 * Layer.
 * @author jroot,ascase
 */
@RestController
@RequestMapping("/api/users")
public class UserController
{
    //creates a reference to the service so we can talk to the server
    private UserService userService;

    /**
     * Constructor
     * @param userService - reference to all of the business logic needed to talk to server
     */
    public UserController(UserService userService)
    {
        super();
        this.userService = userService;
    }

    /**
     * PUT
     * @param user - creates an instance of a user and adds them to the database with a new unique id
     * @return - the information of the user with a new id
     */
    @PostMapping
    public ResponseEntity<User> saveEmployee(@RequestBody User user)
    {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    /**
     * GET
     * @return - list of all users and their information
     */
    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }
    
    /**
     * GET
     * @param id - id of specific user in database
     * @return - displays all information of specific user
     */
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id)
    {
        return new ResponseEntity<User>(userService.getUserById(id),HttpStatus.OK);
    }

    /**
     * UPDATE
     * @param id - unique id of specific user being updated
     * @param user - instance of new user created, and will overwrite existing based on id
     * @return - the existing user at specified id but with the new and updated information
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user)
    {
        return new ResponseEntity<User>(userService.updateUser(user,id), HttpStatus.OK);
    }

    /**
     * DELETE
     * @param id - unique id of specific user being deleted
     * @return - A string that states if deletion was successful or not
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteUser(@PathVariable("id") long id)
    {
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted successfully!", HttpStatus.OK);
    }
    
    


}
