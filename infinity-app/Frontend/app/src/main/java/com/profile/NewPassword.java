package com.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.*;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.AppController;
import com.app.User;
import com.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Sets a new password from the profile page
 */
public class NewPassword extends AppCompatActivity implements View.OnClickListener {

    TextView firstPassText;
    TextView secondPassText;
    TextView errorText;
    EditText firstPassEdit;
    EditText secondPassEdit;
    Button cancelButton;
    Button applyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password_activity);
        initScreen();
    }

    private void initScreen(){
        firstPassText = findViewById(R.id.new_password_text);
        secondPassText = findViewById(R.id.link_text);
        errorText = findViewById(R.id.error_text);

        firstPassEdit = findViewById(R.id.first_password);
        secondPassEdit = findViewById(R.id.link_edit);

        cancelButton = findViewById(R.id.cancel_button);
        applyButton = findViewById(R.id.apply_button);
        cancelButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
    }

    private boolean passwordsEqual(){
        return (firstPassEdit.getText().toString().equals(secondPassEdit.getText().toString()));
    }

    /**
     * Sends the password to the server
     */
    private void setPassword(){
        String newPassword = firstPassEdit.toString();

        try{
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("email", User.getEmail());
            jsonObject.put("password", newPassword);
            jsonObject.put("username", User.getUsername());
            jsonObject.put("displayname", User.getDisplayname());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_CHANGE_PASSWORD,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("PASSWORD", "Success getting response");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PASSWORD", "Failure getting response");
                }
            }) {
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userid", User.getUserID());

                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        }
        catch (JSONException exception){
            Log.d("PASSWORD", "Error changing current password");
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancel_button:
                finish();
                break;
            case R.id.apply_button:
                if(passwordsEqual())
                    setPassword();
                else{
                    errorText.setText("Passwords don't match");
                }
                break;
        }
    }
}
