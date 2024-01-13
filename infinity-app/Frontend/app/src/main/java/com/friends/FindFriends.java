package com.friends;

import static com.friends.FriendsList.friendArrayList;
import static com.friends.PendingFriendList.potentialFriendList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.User;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindFriends extends AppCompatActivity {

    ListView list;
    EditText searchBar;
    ImageButton backButton;
    FindFriends FFclass = this;
    ArrayList<NonFriend> userArrayList = new ArrayList<>();

    private RequestQueue Queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        Queue = Volley.newRequestQueue(this);
        list = findViewById(R.id.list_pot_friends);
        searchBar = findViewById(R.id.search_bar);
        backButton = findViewById(R.id.back_ffriends);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendsList.class);
                startActivity(intent);
            }
        });

        fillUserList();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListAdapterTwo userAdapter = new ListAdapterTwo(FFclass, R.layout.activity_nonfriend, userArrayList);
                list.setAdapter(userAdapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchFilter(editable.toString());
            }
        });
    }

    private void searchFilter(String text) {
        ArrayList<NonFriend> filteredUserList = new ArrayList<>();

        for (NonFriend user : userArrayList) {
            if (user.getDisplayName().toLowerCase().contains(text.toLowerCase())) {
                filteredUserList.add(user);
            }
        }

        ListAdapterTwo userAdapter = new ListAdapterTwo(FFclass, R.layout.activity_nonfriend, filteredUserList);
        list.setAdapter(userAdapter);
    }

    private void fillEmptyPendingFriendList() { // Unfortunate repeat of a method because if the user has not clicked on pending friends list, then this doesn't run
        String TAG = this.getClass().getSimpleName();
        JsonArrayRequest getUsersFriends2 = new JsonArrayRequest(Request.Method.GET,
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
        Queue.add(getUsersFriends2);
    }

    private void fillUserList() {
        String TAG = this.getClass().getSimpleName();
        fillEmptyPendingFriendList();
        JsonArrayRequest getUsers = new JsonArrayRequest(Request.Method.GET,
                Const.POSTMAN_USERS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                System.out.println(potentialFriendList);
                try {
                    for (int i = 0; i < response.length(); ++i) {
                        boolean friendExists = false;
                        boolean pendingFriendExists = false;
                        JSONObject user = response.getJSONObject(i);
                        int userID = user.getInt("id");
                        String displayName = user.getString("displayname");

                        for (int j = 0; j < friendArrayList.size(); ++j) {
                            if (friendArrayList.get(j).getId() == userID) { // If the user is already a friend, make sure to no add him or her to the NonFriend list!
                                friendExists = true;
                            }
                        }
                        for (int k = 0; k < potentialFriendList.size(); ++k) {
                            if (potentialFriendList.get(k).getId() == userID) {
                                pendingFriendExists = true;
                            }
                        }
                        if (!friendExists && !pendingFriendExists && !User.getUserID().equals(userID + "")) {
                            NonFriend newNonFriend = new NonFriend(displayName, displayName + "#" + userID, userID, R.drawable.user_default_image);
                            userArrayList.add(newNonFriend);
                        }
                    }

                    ListAdapterTwo userAdapter = new ListAdapterTwo(FFclass, R.layout.activity_nonfriend, userArrayList);
                    list.setAdapter(userAdapter);

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
        Queue.add(getUsers);
    }
}