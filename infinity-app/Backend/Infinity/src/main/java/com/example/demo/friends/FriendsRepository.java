package com.example.demo.friends;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The repository for all of the friendships in the database
 *
 * @author jroot,ascase
 */
public interface FriendsRepository extends JpaRepository<Friends, Long>
{
    @Query ("SELECT u FROM Friends u WHERE u.userrequester =?1 OR u.userreciever =?1")
    // =?1 means it is looking for the first param (in this case id)
    public List<Friends> findAllFriendsByUserId(long user);
    @Query (value = "SELECT u FROM Friends u WHERE u.userreciever =?1 AND u.userrequester=?2")
    public Friends findFriendshipIdByPair(long u1, long u2);
}
