package com.example.demo.user.service.impl;


import com.example.demo.user.exception.ResourceNotFoundException;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * All of the actual implementation
 * of all of the business logic that is
 * required by the frontend
 * @author jroot,ascase
 */
@Service
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        {
            return user.get();
        }
        else
        {
            throw new ResourceNotFoundException("User", "ID", id);
        }
    }

    @Override
    public User updateUser(User user, long id)
    {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setDisplayname(user.getDisplayname());

        userRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public void deleteUser(long id)
    {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
        userRepository.deleteById(id);
    }

}
