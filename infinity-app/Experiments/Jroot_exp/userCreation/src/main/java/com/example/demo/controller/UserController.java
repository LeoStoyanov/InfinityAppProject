package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles all of the client to backend requests
 * all of these classes hand all requests off to the Service
 * Layer.
 * @author jroot
 */
@RestController
@RequestMapping("/api/users")
public class UserController
{
    //creates a reference to the service so we can talk to the server
    private UserService userService;

    public UserController(UserService userService)
    {
        super();
        this.userService = userService;
    }

}
