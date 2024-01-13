package com.loginscreen;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Leo Stoyanov
 */
/**
 * Class calls a GET request that will return a response containing a JSONArray of users, and
 * it also calls a POST request to enter the user's input credentials into the database.
 */
public class UserRegisterModel {

    private RequestQueue Queue;

    /**
     * Constructor that instantiates a new Vollley request queue based upon the passed
     * context of the UserRegisterActivity class.
     * @param context context of the UserRegisterActivity class
     */
    public UserRegisterModel(Context context) {
        Queue = Volley.newRequestQueue(context);
    }

    /**
     * Preforms a GET request based upon the passed URL and Volley Listener. Returns a response
     * containing a JSONArray of users that is passed onto UserRegisterPresenter through the onSuccessOne
     * method. If the request fails, an error will be passed onto the UserRegisterPresenter through
     * the onError method.
     * @param url API request url
     * @param volleyListener instance of Volley Listener
     */
    public void makeUserGetReq(String url, IVolleyListener volleyListener) {
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

    /**
     * Preforms a POST request based upon the passed URL and Volley Listener. Sends the passed display
     * name, email, password, and generated username using the display name; the backend takes care of
     * appending the ID to the username. If the request is successful, the response is passed through
     * the onSuccessTwo method. If the request fails, an error will be passed onto the UserRegisterPresenter
     * through the onError method.
     * @param url API request url
     * @param volleyListener instance of Volley Listener
     */
    public void makeUserPostReq(String url, IVolleyListener volleyListener, String name, String email, String pass) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("displayname", name);
        params.put("username", name);
        params.put("email", email);
        params.put("password", pass);

        JsonObjectRequest post_req = new JsonObjectRequest(Request.Method.POST,
                Const.REGISTER_CREATE_USER, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyListener.onSuccessTwo(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.onError(error);
            }
        });

        Queue.add(post_req);
    }

}
