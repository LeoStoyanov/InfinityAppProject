package com.dashboard;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.loginscreen.IPresenter;
import com.loginscreen.IView;
import com.loginscreen.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class DashPresenter implements IPresenter, IVolleyListener {

    public final String guildRequest = "GUILD_REQUEST";
    public final String userRequest = "USER_REQUEST";

    private IView view;
    private DashModel model;

    public DashPresenter(IView v, Context context){
        this.view = v;
        this.model = new DashModel(context);
    }

    @Override
    public void loadMessage(String url, String requestType) {
        if(requestType.equals(guildRequest)){

        }
        else if (requestType.equals(userRequest)){

        }
        else {

        }
    }

    @Override
    public void onSuccessOne(JSONArray result) {
    }

    @Override
    public void onSuccessTwo(JSONObject result) {
    }

    @Override
    public void onError(VolleyError error) {

    }
}
