package com.app;

/**
 * Stores user information
 */
public class User {

    private static String username = null;
    private static String displayname;
    private static String userID = "1";
    private static String email;
    private static String pfpurl;

    /*
    Set methods
     */
    public static void setUsername(String user){ username = user; }
    public static void setDisplayname(String display) { displayname = display; }
    public static void setUserID(String ID) { userID = ID;}
    public static void setEmail(String newEmail) { email = newEmail; }
    public static void setPfpurl(String url) { pfpurl = url; }

    /*
    Accessor methods
     */
    public static String getUsername(){ return username; }
    public static String getDisplayname() {
        if(displayname == null)
            return username;
        return displayname;
    }
    public static String getUserID() { return userID; }
    public static String getEmail() { return email; }
    public static String getPfpurl() { return pfpurl; }

}