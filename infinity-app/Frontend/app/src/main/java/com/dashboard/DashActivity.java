package com.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.app.GuildRecyclerAdapter;
import com.app.User;
import com.app.Presenter;
import com.app.Server;
import com.friends.*;
import com.loginscreen.IPresenter;
import com.app.IView;
import com.net_utils.Const;
import com.notes.NotesList;
import com.profile.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashActivity extends AppCompatActivity implements IView, View.OnClickListener {

    public final String guildRequest = "GUILD_REQUEST";
    public final String userRequest = "USER_REQUEST";

    public String username;
    public TextView welcome_text;
    public RecyclerView server_recycler;
    private IPresenter presenter;
    private GuildRecyclerAdapter mAdapter;
    private List<Server> serverList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        presenter = new Presenter(this, getApplicationContext());
        serverList = new ArrayList<>();

        server_recycler = findViewById(R.id.server_recyclerview);
        mAdapter = new GuildRecyclerAdapter(DashActivity.this, serverList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        server_recycler.setLayoutManager(mLayoutManager);
        server_recycler.setItemAnimator(new DefaultItemAnimator());
        server_recycler.setAdapter(mAdapter);

        presenter.loadMessage(Const.URL_JSON_SERVER_NAME, guildRequest);
        presenter.loadMessage(Const.URL_JSON_NAME+ User.getUserID(), userRequest);

        ImageButton profile_button = findViewById(R.id.profile_button);
        profile_button.setOnClickListener(this);
        ImageButton friends_button = findViewById(R.id.friends_button);
        friends_button.setOnClickListener(this);
        Button private_button = findViewById(R.id.private_button);
        private_button.setOnClickListener(this);

        ImageView private_image = findViewById(R.id.private_image);
        private_image.setImageResource(R.drawable.user_no_ring_image);
        ImageView add_image = findViewById(R.id.add_image);
        add_image.setImageResource(R.drawable.plus_image);

        welcome_text = findViewById(R.id.welcome_text);
        welcome_text.setText(R.string.loading);
    }

    /**
     * Handles response for JSONObject object
     * @param view
     */
    public void useResponseTwo(JSONObject view){
        Log.d("DASHACTIVITY", "useResponseTwo started");
        try {
            welcome_text.setText("Welcome back, " + view.getString("displayname") + "!");
            User.setUsername(view.getString("username"));
            User.setDisplayname(view.getString("displayname"));
            User.setEmail(view.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
            username = "caught";
        }
    }

    /**
     * Handles response for JSONArray object
     * @param view response from the API request
     */
    @Override
    public void useResponseOne(JSONArray view) {
        Log.d("DASHACTIVITY", "useResponseOne started");

        try{
            for(int i = 0; i < view.length(); i++){
                JSONObject newJson = view.getJSONObject(i);
                JSONArray guildMembers = newJson.getJSONArray("userlist");

                for(int j = 0; j < guildMembers.length(); j++){
                    if(guildMembers.get(j).toString().contains("\"id\":" + User.getUserID())){
                        String serverName = newJson.getString("guildname");
                        Server newServer = new Server(newJson.getString("id"));
                        newServer.setServerName(serverName);
                        serverList.add(newServer);
                    }
                }
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        if(serverList.size() == 0){
            Log.d("No Guilds", "No guilds were found for this user");
            serverList.add(new Server("0"));
        }
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.profile_button:
                startActivity(new Intent(DashActivity.this, ProfileActivity.class));
                break;
            case R.id.friends_button:
                startActivity(new Intent(DashActivity.this, FriendsList.class));
                break;
            case R.id.private_button:
                startActivity(new Intent(DashActivity.this, NotesList.class));
                break;
            default:
                break;
        }
    }
}