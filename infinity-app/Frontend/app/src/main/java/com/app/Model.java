package com.app;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loginscreen.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class Model {

    private RequestQueue Queue;

    public Model(Context context) {
        Queue = Volley.newRequestQueue(context);
    }

    public void httpsRequest (String url, String requestType, final IVolleyListener volleyListener){
        Log.d("DASHMODEL", "httpsRequest started");

        if(requestType.equals("GUILD_REQUEST") || requestType.equals("CHAT_REQUEST")){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("DASHMODEL", "onResponse successful");
                    volleyListener.onSuccessOne(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DASHMODEL", "onErrorResponse successful");
                    volleyListener.onError(error);
                }
            });
            Queue.add(jsonArrayRequest);
        }
        else{
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("DASHMODEL", "onResponse successful");
                    volleyListener.onSuccessTwo(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DASHMODEL", "onErrorResponse successful");
                    volleyListener.onError(error);
                }
            });
            Queue.add(jsonArrayRequest);
        }


        Log.d("DASHMODEL", "httpsRequest finished");
    }
}
