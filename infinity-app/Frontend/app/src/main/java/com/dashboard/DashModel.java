package com.dashboard;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DashModel {

    private RequestQueue Queue;

    public DashModel(Context context) {
        Queue = Volley.newRequestQueue(context);
    }

    public void getGuilds(){

    }

    public void getUser(){

    }
}
