package com.guild;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.app.AppController;
import com.app.GuildRecyclerAdapter;
import com.app.IView;
import com.app.Server;
import com.dashboard.*;
import com.app.User;
import com.app.Presenter;
import com.net_utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Guild home page
 */
public class GuildActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private List<Server> serverList;
    private String guildID;
    private Presenter presenter;
    private GuildRecyclerAdapter mAdapter;
    private EditText messageEdit;
    private WebSocketClient cc;
    private TextView chatBox;
    private String chatID = "0";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        chatID = getIntent().getStringExtra("chatID");

        setContentView(R.layout.guild_home_activity);
        guildID = GuildRecyclerAdapter.guildID;

        Intent intent = getIntent();
        guildID = intent.getStringExtra("guildID");

        chatBox = findViewById(R.id.output_text);
        chatBox.setText("");
        chatBox.setMovementMethod(new ScrollingMovementMethod());

        Button notesButton = findViewById(R.id.notes_button);
        notesButton.setText("Notes");
        notesButton.setOnClickListener(this);

        Button dashButton = findViewById(R.id.home_button);
        dashButton.setText("Dashboard");
        dashButton.setOnClickListener(this);

        Button drawingsButton = findViewById(R.id.drawings_button);
        drawingsButton.setText("Drawings");
        drawingsButton.setOnClickListener(this);

        RecyclerView server_recycler = findViewById(R.id.recyclerView);

        mAdapter = new GuildRecyclerAdapter(GuildActivity.this, serverList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        server_recycler.setLayoutManager(mLayoutManager);
        server_recycler.setItemAnimator(new DefaultItemAnimator());
        server_recycler.setAdapter(mAdapter);

        presenter = new Presenter(this, getApplicationContext());
        presenter.loadMessage(Const.URL_JSON_SERVER_NAME, "GUILD_REQUEST");

        //TODO --> fix this once backend is done
        String w = "ws://echo.websocket.org" + guildID + chatID;
        connect(w);

        Button chatsButton = findViewById(R.id.popup_button);
        chatsButton.setOnClickListener(this);
        Button sendMessageButton = findViewById(R.id.send_button);
        sendMessageButton.setOnClickListener(this);

        messageEdit = findViewById(R.id.message_edit);
    }

    public GuildActivity(){
        serverList = new ArrayList<>();
        guildID = "0";
    }

    public void setGuildID (String guildID){
        this.guildID = guildID;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.notes_button:
                startActivity(new Intent(GuildActivity.this, GuildNotesActivity.class));
                break;
            case R.id.drawings_button:
                startActivity(new Intent(GuildActivity.this, GuildDrawingsActivity.class));
                break;
            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(), DashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.popup_button:
                Log.d("BUTTON", "Chat popup button pressed");
                startActivity(new Intent(GuildActivity.this, GuildChatListActivity.class));
                break;
            case R.id.send_button:
                Log.d("BUTTON", "Send button pressed");
                //cc.send(messageEdit.toString());
                output("You: " + messageEdit.getText());
                messageEdit.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void useResponseOne(JSONArray view) {
        Log.d("GUILDACTIVITY", "useResponseOne started");

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

    @Override
    public void useResponseTwo(JSONObject view) {

    }

    public void connect(String w){
        Draft[] drafts = { new Draft_6455() };

        try {
            Log.d("SOCKET", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    messageEdit.setText(message);
                    output("Receiving: " + message);
                }

                @Override
                public void onOpen(ServerHandshake handshakeData) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("Exception: ", ex.toString());
                }
            };
        } catch (
                URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
    }

    private void output(String txt){
        chatBox.setText(chatBox.getText().toString() + txt + "\n\n");

        final int scrollAmt = chatBox.getLayout().getLineTop(chatBox.getLineCount()) - chatBox.getHeight();
        /*
        if(scrollAmt > 0)
            chatBox.scrollTo(0,scrollAmt);
        else         */
        chatBox.scrollTo(0,0);
    }
}
