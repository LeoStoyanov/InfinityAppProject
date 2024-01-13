package com.example.demo.friends;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Is an intermediate class that separates the controller logic from the business logic
 */
public interface
FriendsService
{
    /**
     * Gets all relationships in the database
     * @return - list of all friendships in database
     */
    public List<Friends> getAllFriendsInDatabase();

    /**
     * Take one users friendlist based on id
     * @param id - id of user's friendslist we are referencing
     * @return - list of this users friends
     */
    List<Friends> getAllFriends(long id);

    /**
     *
     * @param thisUserId -
     * @param friendId
     * @return
     */
    ResponseEntity<String> deleteFriend(long thisUserId,long friendId);

    /**
     *
     * @param thisUserId
     * @param friendId
     * @return
     */
    ResponseEntity<Friends> acceptFriend(long thisUserId,long friendId);

    /**
     * Blah
     * @param thisUserId
     * @param friendId
     * @return
     */
    ResponseEntity<Friends> sendRequest(long thisUserId,long friendId);
}
