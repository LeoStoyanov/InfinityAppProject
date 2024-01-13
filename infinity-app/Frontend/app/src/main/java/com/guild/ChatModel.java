package com.guild;

import android.util.Log;

import com.loginscreen.IVolleyListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatModel {

    private WebSocketClient cc;

    public ChatModel() {}

    public void sendMessage(String message){
        cc.send(message.toString());
    }

    public void connect(String w, final IVolleyListener volleyListener){
        Draft[] drafts = { new Draft_6455() };
/*
        try {
            Log.d("SOCKET", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    //Set text here
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

 */
    }
}
