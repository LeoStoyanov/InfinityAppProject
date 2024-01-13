package com.app;

import android.app.Activity;

public class Server extends Activity {

    private String serverName;
    private String serverPic;
    private String serverID;

    /**
     * Holds information for each guild
     * @param serverID
     */
    public Server(String serverID){
        this.serverID = serverID;
    }

    public String getServerName(){
        if(serverID.equals("0"))
            return "No servers available";
        return serverName;
    }

    public String getImage(){
        return serverPic;
    }
    public String getServerID() { return serverID; }

    public void setServerName(String newName){
        serverName = newName;
    }

    public void setServerPic(String serverPic) { this.serverPic = serverPic;}
}
