package com.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.app.AppController;
import com.app.IView;
import com.app.User;
import com.app.Presenter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Retrieves user information and displays it
 */
public class ProfileActivity extends Activity implements IView, View.OnClickListener {

    ImageView profileImage;
    String image_url = "";
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.profile_activity);

        profileImage = findViewById(R.id.profile_image);
        profileImage.setImageResource(R.drawable.user_default_image);

        TextView public_name_text = findViewById(R.id.public_name_text);
        public_name_text.setText("Display name: " + User.getDisplayname());
        TextView username_text = findViewById(R.id.username_text);
        username_text.setText("Username: " + User.getUsername());
        TextView email_text = findViewById(R.id.email_text);
        email_text.setText("Email: " + User.getEmail());

        ImageButton home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(this);
        Button pfp_button = findViewById(R.id.edit_pfp_button);
        pfp_button.setOnClickListener(this);
        Button password_button = findViewById(R.id.change_pw_button);
        password_button.setOnClickListener(this);

        presenter = new Presenter(this, getApplicationContext());
        presenter.loadMessage(Const.URL_PROFILE_IMAGE, "PFP_REQUEST");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_button:
                finish();
                break;
            case R.id.edit_pfp_button:
                ImagePicker.with(ProfileActivity.this)
                        .crop()
                        .start();
                break;
            case R.id.change_pw_button:
                startActivity(new Intent(ProfileActivity.this, NewPassword.class));
                break;
        }
    }

    @Override
    public void useResponseOne(JSONArray view) {

    }

    @Override
    public void useResponseTwo(JSONObject view) {
        /*
        Log.d("URL", "URL retrieved");
        try {
            image_url = view.getString("url");
            Log.d("URL", "URL received: " + image_url);
            User.setPfpurl(image_url);
        } catch (JSONException e) {
            Log.d("URL", "Error setting image_url to response");
            e.printStackTrace();
            image_url = "error";
        }*/

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Image request after getting user url
                if(!image_url.equals("")){
                    RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
                    ImageRequest imageRequest = new ImageRequest(image_url, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            if(response != null)
                                profileImage.setImageBitmap(response);
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    AppController.getInstance().addToRequestQueue(imageRequest);
                }
            }
        }, 100);
    }
}
