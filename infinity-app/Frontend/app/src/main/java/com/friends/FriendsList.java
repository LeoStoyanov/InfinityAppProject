package com.friends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.User;
import com.dashboard.DashActivity;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * @author Leo Stoyanov
 */
/**
 * Class creates and sets up the layout of the Friends screen, including the list
 * of friends the ability to search for and add users as friends.
 */
public class FriendsList extends AppCompatActivity {

    ListView list;
    FriendsList Fclass = this;
    ImageButton homeButton;
    ImageButton pFriendsListButton;
    ImageButton fFriendsListButton;
    static ArrayList<Friend> friendArrayList = new ArrayList<>();

    private RequestQueue Queue;

    /**
     * Loads the activity_friends_list xml file, assigns field variables to the components
     * of the layout, instantiates a new Volley request queue, and calls the fillFriendList method
     * to show the user his or her list of friends.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Queue = Volley.newRequestQueue(this);
        list = findViewById(R.id.list_friends);
        homeButton = findViewById(R.id.home_button);
        pFriendsListButton = findViewById(R.id.pfriends_list_button);
        fFriendsListButton = findViewById(R.id.ffriends_list_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashActivity.class);
                startActivity(intent);
            }
        });

        pFriendsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PendingFriendList.class);
                startActivity(intent);
            }
        });

        fFriendsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindFriends.class);
                startActivity(intent);
            }
        });

        friendArrayList.clear();
        fillFriendList();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // A user will be able to message the friend (item) on which he or she clicks.
            }
        });
    }

    /**
     * Fetches and processes the user's friends from the database.
     * <p>
     * NOTE: method is WIP because I had to create my own users via Postman, and so this
     * method is very inefficient.
     */
    private void fillFriendList() {
        String TAG = this.getClass().getSimpleName();
        JsonArrayRequest getUsersFriends = new JsonArrayRequest(Request.Method.GET,
                Const.GET_FRIENDS + User.getUserID(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                try {
                    for (int i = 0; i < response.length(); ++i) {
                        JSONObject user = response.getJSONObject(i);
                        int userID = user.getInt("id");
                        String displayName = user.getString("displayname");
                        Friend newFriend = new Friend(displayName, displayName + "#" + userID, userID, R.drawable.user_default_image);
                        friendArrayList.add(newFriend);
                    }

                    ListAdapter friendAdapter = new ListAdapter(Fclass, R.layout.activity_friend, friendArrayList);
                    list.setAdapter(friendAdapter);

                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            int itemToDelete = i;
                            int deleteFriendId = friendArrayList.get(i).getId();
                            new AlertDialog.Builder(FriendsList.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Are you sure?")
                                    .setMessage("Do you want to delete" + friendArrayList.get(i).getDisplayName() + " ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            friendArrayList.remove(itemToDelete);
                                            postDeleteFriend(deleteFriendId);
                                            friendAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();

                            return true;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        Queue.add(getUsersFriends);
    }

    private void postDeleteFriend(int deleteFriendId) {
        String TAG = this.getClass().getSimpleName();

        JsonObjectRequest post_req = new JsonObjectRequest(Request.Method.POST,
                Const.DELETE_FRIEND + User.getUserID() + "/" + deleteFriendId, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        Queue.add(post_req);

    }
}
