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
 * Class acts as the presenter between the UserRegisterModel and UserRegisterActivity view class.
 * Handles method calls between the aforementioned classes.
 */
public class UserRegisterPresenter implements IPresenter, IVolleyListener{
    private IView view;
    private String TAG;
    private UserRegisterModel model;
    private String name;
    private String email;
    private String pass;

    /**
     * Constructor that utilizes the View interface implemented in the UserRegisterActivity class
     * along with the context of the UserRegisterActivity class. Creates a new instance of the
     * UserRegisterModel class to preform API requests for the view class. Also, the passed display
     * name, email, and password are assigned to their respective field variables.
     * @param v View of LoginActivity
     * @param context Context of the LoginActivity
     */
    public UserRegisterPresenter(IView v, Context context, String name, String email, String pass) {
        TAG = this.getClass().getSimpleName();
        model = new UserRegisterModel(context);
        this.view = v;
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    /**
     * Calls a method in the UserRegisterModel class, and will preform a POST request, utilizing
     * the passed url, Volley Listener, display name, email, and password, or will preform a
     * GET request, utilizing the passed url and Volley Listener.
     * @param url request url
     * @param requestType type of request, either GET, PUT, or POST
     */
    @Override
    public void loadMessage (String url, String requestType) {
        if (requestType == "get") {
            model.makeUserGetReq(url, this);
        }
        else if (requestType == "post") {
            model.makeUserPostReq(url, this, name, email, pass);
        }
    }

    /**
     * If UserRegisterModel class's GET request is successful, it will return a JSONArray of
     * users. Also, Volley will log the GET request.
     * @param result response from the GET request
     */
    @Override
    public void onSuccessOne(JSONArray result) {
        Log.d(TAG, result.toString());
        view.useResponse(result);
    }

    /**
     * If UserRegisterModel class's POST request is successful,Volley will log the POST request.
     * @param result response from the POST request
     */
    @Override
    public void onSuccessTwo(JSONObject result) {
        Log.d(TAG, result.toString());
    }

    /**
     * If the UserRegisterModel class's GET or POST requests fail, it will return an error
     * that Volley will log.
     * @param error error to be logged by Volley
     */
    @Override
    public void onError(VolleyError error) {
        VolleyLog.d(TAG, "Error: " + error.getMessage());
    }
}
