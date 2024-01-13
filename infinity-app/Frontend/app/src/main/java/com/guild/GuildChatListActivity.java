package com.guild;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ChatRecyclerAdapter;
import com.app.GuildRecyclerAdapter;
import com.app.IView;
import com.R;
import com.app.Presenter;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuildChatListActivity extends Activity implements IView, View.OnClickListener {

    ArrayList<ChatLink> chatsList;
    ChatRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.chat_list_activity);

        /*Get HTTP request of chat list, then it goes back to useresponseone*/
        Presenter presenter = new Presenter(this, getApplicationContext());
        //TODO --> need a url for this
        presenter.loadMessage(Const.GUILD_CHAT_LIST, "CHAT_REQUEST");

        /*Initialize recyclerview*/
        RecyclerView chatRecycler = findViewById(R.id.chat_recycler);

        /*Set up adapter and layout manager*/
        //TODO --> chatsList
        chatsList = new ArrayList();
        mAdapter = new ChatRecyclerAdapter(GuildChatListActivity.this, chatsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecycler.setLayoutManager(layoutManager);
        chatRecycler.setItemAnimator(new DefaultItemAnimator());
        chatRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void useResponseOne(JSONArray view) {
        Log.d("GUILDCHAT", "useResponseOne started");
/*
        try{
            for (int i = 0; i < view.length(); i++) {
                JSONObject newJson = view.getJSONObject(i);

                String chatID = newJson.getString("chatID");
                String chatName = newJson.getString("chatName");

                Log.d("CHAT VALUES", "chatID: " + chatID + ", chatName: " + chatName);

                ChatLink link = new ChatLink(chatID, chatName);
                chatsList.add(link);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();

 */
    }

    @Override
    public void useResponseTwo(JSONObject view) {

    }
}
