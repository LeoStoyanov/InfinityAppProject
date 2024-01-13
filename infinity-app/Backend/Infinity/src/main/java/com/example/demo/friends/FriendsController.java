package com.example.demo.friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController
{
    @Autowired
    private FriendsService friendsService;

    /**
     * Lists all friends in the database
     * @return - a list of all friendships within the database
     */
    @GetMapping("/database")
    public List<Friends> getAllFriendsInDatabase()
    {
        return friendsService.getAllFriendsInDatabase();
    }

    /**
     * Lists all friends of specific user
     * @return - A list of friendships for specific user
     */
    @GetMapping("/myfriends/{id}")
    public List<Friends> getAllFriends(@PathVariable ("id") long id)
    {
        return friendsService.getAllFriends(id);
    }

    /**
     * Deletes a friend of a specific user
     * @param thisUserId - Users friendslist we are referencing
     * @param friendId - the friend in the friendslist we are deleting
     * @return - HTTP Status
     */
    @DeleteMapping("/delete/{thisUserId}/{friendId}")
    public ResponseEntity<String> deleteFriend (@PathVariable long thisUserId,@PathVariable long friendId)
    {
        return friendsService.deleteFriend(thisUserId, friendId);
    }


    /**
     *
     * @param thisUserId - Users friendslist we are referencing
     * @param friendId - the friend in the friendslist we are accepting
     * @return - HTTP Status
     */
    @PutMapping("/accept/{thisUserId}/{friendId}")
    public ResponseEntity<Friends> acceptFriend (@PathVariable long thisUserId,@PathVariable long friendId)
    {
        return  friendsService.acceptFriend(thisUserId, friendId);
    }

    /**
     *
     * @param thisUserId - Users friendslist we are referencing/adding to
     * @param friendId - the friend in the database that we are sending a request
     * @return - HTTP Status
     */
    @GetMapping("/sendRequest/{thisUserId}/{friendId}")
    public ResponseEntity<Friends> sendRequest (@PathVariable long thisUserId,@PathVariable long friendId)
    {
        return  friendsService.sendRequest(thisUserId, friendId);
    }


}
