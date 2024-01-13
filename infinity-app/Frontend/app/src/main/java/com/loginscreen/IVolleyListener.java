package com.loginscreen;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * @author Leo Stoyanov
 */
/**
 * Interface that uses the Volley library and is implemented within presenter classes.
 */
public interface IVolleyListener {
    /**
     * Uses a JSONArray response from a GET request, Volley logs the request, and then
     * calls the useResponse method of the respective view class that will use the response.
     * @param result response from the GET request
     */
    public void onSuccessOne(JSONArray result);
    /**
     * Uses a JSONObject response from a GET request, Volley logs the request, and then
     * calls the useResponse method of the respective view class that will use the response.
     * @param result response from the GET request
     */
    public void onSuccessTwo(JSONObject result);

    /**
     * If the API request fails, the error will be logged by Volley.
     * @param error error to be logged by Volley
     */
    public void onError(VolleyError error);
}
