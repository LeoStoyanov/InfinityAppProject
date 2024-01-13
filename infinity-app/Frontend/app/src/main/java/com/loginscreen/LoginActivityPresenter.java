package com.loginscreen;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * @author Leo Stoyanov
 */
/**
 * Class acts as the presenter between the LoginActivityModel and LoginActivity view class. Handles
 * method calls between the aforementioned classes.
 */
public class LoginActivityPresenter implements IPresenter, IVolleyListener {
    private IView view;
    private String TAG;
    private LoginActivityModel model;

    /**
     * Constructor that utilizes the View interface implemented in the LoginActivity class
     * along with the context of the LoginActivity class. Creates a new instance of the
     * LoginActivityModel class to preform API requests for the view class.
     * @param v View of LoginActivity
     * @param context Context of the LoginActivity
     */
    public LoginActivityPresenter(IView v, Context context) {
        TAG = this.getClass().getSimpleName();
        model = new LoginActivityModel(context);
        this.view = v;
    }

    /**
     * Calls a method in the LoginActivityModel class, which will preform a GET request, utilizing
     * the passed url and Volley Listener.
     * @param url request url
     * @param requestType type of request, either GET, PUT, or POST
     */
    @Override
    public void loadMessage(String url, String requestType) {
        if (requestType == "get") {
            model.makeLoginGetReq(url, this);
        }
    }

    /**
     * If LoginActivityModel class's GET request is successful, it will return a JSONArray of
     * users. Also, Volley will log the GET request.
     * @param result response from the GET request
     */
    @Override
    public void onSuccessOne(JSONArray result) {
        Log.d(TAG, result.toString());
        view.useResponse(result);
    }

    /**
     * Unused method because LoginActivity does not GET request any JSONObjects.
     * @param result response from the GET request
     */
    @Override
    public void onSuccessTwo(JSONObject result) {
        // Not used here
    }

    /**
     * If the LoginActivitModel class's GET request fails, it will return an error that Volley will
     * log.
     * @param error error to be logged by Volley
     */
    @Override
    public void onError(VolleyError error) {
        VolleyLog.d(TAG, "Error: " + error.getMessage());
    }
}
