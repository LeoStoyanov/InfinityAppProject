package com.example.demo.friends;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FriendsServiceImpl implements FriendsService
{
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<Friends> getAllFriendsInDatabase()
    {
        return friendsRepository.findAll();
    }

    @Override
    public List<Friends> getAllFriends(long id)
    {
        return friendsRepository.findAllFriendsByUserId(id);
    }

    @Override
    public ResponseEntity<String> deleteFriend(long thisUserId,long friendId)
    {
        Friends existingFid = friendsRepository.findFriendshipIdByPair(thisUserId, friendId);
        Friends existingFid2 = friendsRepository.findFriendshipIdByPair(friendId, thisUserId);
        friendsRepository.deleteById(existingFid.getId());
        friendsRepository.deleteById(existingFid2.getId());
        return new ResponseEntity<String>("Friend termination successfull!", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Friends> acceptFriend(long thisUserId,long friendId)
    {
        Friends existingFid = friendsRepository.findFriendshipIdByPair(thisUserId, friendId);
        existingFid.setAcceptedFriend(true);
        Friends existingFid2 = friendsRepository.findFriendshipIdByPair(friendId, thisUserId);
        existingFid2.setAcceptedFriend(true);
        friendsRepository.save(existingFid);
        friendsRepository.save(existingFid2);
        return new ResponseEntity<Friends>(existingFid,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Friends> sendRequest(long thisUserId,long friendId)
    {
        userService.getUserById(thisUserId);
        userService.getUserById(friendId);
        Friends newFriend = new Friends(thisUserId, friendId);
        Friends newFriend2 = new Friends(friendId, thisUserId);
        friendsRepository.save(newFriend2);
        return new ResponseEntity<>(friendsRepository.save(newFriend), HttpStatus.CREATED);
    }
}
