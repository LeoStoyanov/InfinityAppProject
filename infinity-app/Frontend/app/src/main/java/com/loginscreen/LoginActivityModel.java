package com.loginscreen;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
/**
 * @author Leo Stoyanov
 */
/**
 * Class calls a GET request that will return a response containing a JSONArray of users.
 */
public class LoginActivityModel {

    private RequestQueue Queue;

    /**
     * Constructor that instantiates a new Vollley request queue based upon the passed
     * context of the LoginActivity class.
     * @param context context of the LoginActivity class
     */
    public LoginActivityModel(Context context) {
        Queue = Volley.newRequestQueue(context);
    }

    /**
     * Preforms a GET request based upon the passed URL and Volley Listener. Returns a response
     * containing a JSONArray of users that is passed onto LoginActivityPresenter through the onSuccessOne
     * method. If the request fails, an error will be passed onto the LoginActivityPresenter through
     * the onError method.
     * @param url API request url
     * @param volleyListener instance of Volley Listener
     */
    public void makeLoginGetReq(String url, IVolleyListener volleyListener) {

        JsonArrayRequest json_obj = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        volleyListener.onSuccessOne(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.onError(error);
            }
        });

        Queue.add(json_obj);
    }

}
