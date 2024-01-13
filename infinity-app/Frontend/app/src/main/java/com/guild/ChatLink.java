package com.guild;

import android.app.Activity;

public class ChatLink extends Activity {

    private String chatName;
    private String chatID;
    //private String guildID;

    /**
     * Holds name, ID, and guild ID for each chat
     */
    public ChatLink(String chatID, String chatName){
        this.chatID = chatID;
        this.chatName = chatName;
        //this.guildID = guildID;
    }

    public String getChatName(){return chatName;}
    public String getChatID() {return chatID;}
    //public String getGuildID() {return guildID;}

}
