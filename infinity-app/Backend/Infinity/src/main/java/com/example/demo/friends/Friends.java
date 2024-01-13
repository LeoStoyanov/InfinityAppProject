package com.example.demo.friends;

import com.example.demo.ConfirmedResponse;
import com.example.demo.draw.DrawGuild;
import com.example.demo.guild.RoleTypes;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Friends Object that links two Users
 */
@Entity
@Table(name = "friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Aaron says many to many

    private long userrequester;

    private long userreciever;

    private boolean acceptedFriend;

    public Friends() {

    }

    public Friends(long userrequester, long userreciever) {
        this.userrequester = userrequester;
        this.userreciever = userreciever;
        this.acceptedFriend = false;
    }

    public void setAcceptedFriend(boolean acceptedFriend) {
        this.acceptedFriend = acceptedFriend;
    }

    public boolean isAcceptedFriend() {
        return acceptedFriend;
    }

    public long getId() {
        return id;
    }

    public long getUserrequester() {
        return userrequester;
    }

    public long getUserreciever() {
        return userreciever;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserrequester(long userrequester) {
        this.userrequester = userrequester;
    }

    public void setUserreciever(long userreciever) {
        this.userreciever = userreciever;
    }

//    public ConfirmedResponse ConfirmedFriends(int testCase, long id, long userreciever, long userrequester, boolean acceptedFriend) {
//        ConfirmedResponse result = new ConfirmedResponse();
//        result.setApproved(false);
//        switch (testCase) {
//            case (0): {
//                result.setApproved(true);
//                return result;
//            }
//            case (1): {
//                result.setMessage("Error.id.Missmatch");
//                long inf = Integer.MAX_VALUE;
//                setId(inf);
//                if ((id != this.id) && (getId() == inf)) {
//                    result.setApproved(true);
//                    result.setMessage("null");
//                }
//                return result;
//            }
//            case (2): {
//                result.setMessage("Error.receiver.Missmatch");
//                setUserreciever(1);
//                if ((!(userreciever == (this.userreciever))) && (getUserreciever() == (1))) {
//                    result.setApproved(true);
//                    result.setMessage("null");
//                }
//                return result;
//            }
//            case (3): {
//                result.setMessage("Error.requester.Missmatch");
//                setUserrequester(1);
//                if ((!(userrequester == (this.userrequester))) && (getUserrequester() == (1))) {
//                    result.setApproved(true);
//                    result.setMessage("null");
//                }
//                return result;
//            }
//            case (4): {
//                result.setMessage("Error.accepted.Missmatch");
//                setAcceptedFriend(true);
//                if ((!(acceptedFriend == (this.acceptedFriend))) && (isAcceptedFriend() == (true))) {
//                    result.setApproved(true);
//                    result.setMessage("true");
//                }
//                return result;
//            }
//        }
//        return result;
//    }
}


