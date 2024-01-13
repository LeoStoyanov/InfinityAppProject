package com.friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingFriendList extends AppCompatActivity {
    ListView list;
    ImageButton backButton;
    PendingFriendList PFclass = this;
    static ArrayList<PendingFriend> potentialFriendList = new ArrayList<>();

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
        setContentView(R.layout.activity_pendingfriends_list);

        backButton = findViewById(R.id.back_pfriends);
        Queue = Volley.newRequestQueue(this);
        list = findViewById(R.id.list_pendingfriends);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendsList.class);
                startActivity(intent);
            }
        });

        fillPendingFriendList();
    }

    /**
     * Fetches and processes the user's friends from the database.
     * <p>
     * NOTE: method is WIP because I had to create my own users via Postman, and so this
     * method is very inefficient.
     */
    public void fillPendingFriendList() {
        String TAG = this.getClass().getSimpleName();
        JsonArrayRequest getUsersFriends = new JsonArrayRequest(Request.Method.GET,
                Const.POSTMAN_PENDINGFRIENDS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                potentialFriendList.clear();
                try {
                    for (int i = 0; i < response.length(); ++i) {
                        JSONObject user = response.getJSONObject(i);
                        int userID = user.getInt("id");
                        String displayName = user.getString("displayname");
                        PendingFriend newPFriend = new PendingFriend(displayName, displayName + "#" + userID, userID, R.drawable.user_default_image);
                        potentialFriendList.add(newPFriend);
                    }
                    PendingAdapter pendingAdapter = new PendingAdapter(PFclass, R.layout.activity_pending_friend, potentialFriendList);
                    list.setAdapter(pendingAdapter);
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
}
