package com.app;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.app.Model;
import com.loginscreen.IPresenter;
import com.app.IView;
import com.loginscreen.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class Presenter implements IPresenter, IVolleyListener {

    private IView view;
    private Model model;

    public Presenter(IView v, Context context){
        this.view = v;
        this.model = new Model(context);
    }

    @Override
    public void loadMessage(String url, String requestType) {
        Log.d("DASHPRESENTER", "loadMessage started");
        model.httpsRequest(url, requestType, this);
    }

    /**
     * Success on this response sets the guild view
     * @param result response from the GET request
     */
    @Override
    public void onSuccessOne(JSONArray result) {
        Log.d("DASHPRESENTER", "onSuccessOne started");

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.useResponseOne(result);
            }
        },1000);
    }

    /**
     * Success on this response sets the user info
     * @param result response from the GET request
     */
    @Override
    public void onSuccessTwo(JSONObject result) {
        Log.d("DASHPRESENTER", "onSuccessTwo started");
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.useResponseTwo(result);
            }
        },1000);
    }

    @Override
    public void onError(VolleyError error) {
        Log.d("DASHPRESENTER", "onError started");
    }
}
