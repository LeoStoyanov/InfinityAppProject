package com.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.AppController;
import com.app.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.net_utils.Const;
import com.profile.NewPassword;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Deprecated class!
 */
/*
public class ProfileActivity extends Activity {

    ImageView profile_image;
    String image_url = "";
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.profile_activity);
        //initPic();
        initText();
        initButtons();
    }

    private void initPic() {
        profile_image = findViewById(R.id.profile_image);

        if(User.getPfpurl() == null){
            //Get user image url
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    Const.URL_PROFILE_IMAGE, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("URL", "URL retrieved");
                            try {
                                image_url = response.getString("url");
                                Log.d("URL", "URL received: " + image_url);
                                User.setPfpurl(image_url);
                            } catch (JSONException e) {
                                Log.d("URL", "Error setting image_url to response");
                                e.printStackTrace();
                                image_url = "error";
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    image_url = "error";
                    Log.d("URL", "Error getting url");
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }
        else
            image_url = User.getPfpurl();

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
                            profile_image.setImageBitmap(response);
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    AppController.getInstance().addToRequestQueue(imageRequest);
                }
            }
        }, 1000);
    }

    private void initText(){
        ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
        profileImage.setImageResource(R.drawable.user_default_image);

        TextView public_name_text = (TextView) findViewById(R.id.public_name_text);
        public_name_text.setText("Display name: " + User.getDisplayname());

        TextView username_text = (TextView) findViewById(R.id.username_text);
        username_text.setText("Username: " + User.getUsername());

        TextView email_text = (TextView) findViewById(R.id.email_text);
        email_text.setText("Email: " + User.getEmail());

        Button changePass = findViewById(R.id.change_pw_button);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, NewPassword.class));
            }
        });
    }

    private void initButtons(){
        ImageButton home_button = (ImageButton) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button pfp_button = (Button) findViewById(R.id.edit_pfp_button);
        pfp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileActivity.this)
                        .crop()
                        .start();
            }

        });

        Button password_button = (Button) findViewById(R.id.change_pw_button);
        password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ProfileActivity.this, NewPassword.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                profile_image.setImageBitmap(selectedImage);
                updateUsersImage(); // Post the user's selected image to the database
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "There was an error selecting your image.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked an image image.",Toast.LENGTH_LONG).show();
        }

    }

    private Map<String, String> prepareImage(){

        Map<String, String> params = new HashMap<String, String>();

        String result = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            if (selectedImage != null){
                byteArrayOutputStream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byte[] bitmapBytes = byteArrayOutputStream.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
                params.put("image", result);
            }
        } catch (IOException e){
            Toast.makeText(getApplicationContext(), "Error converting image.",Toast.LENGTH_LONG).show();
        }

        return params;
    }


    private void updateUsersImage() {
        String TAG = ProfileActivity.class.getSimpleName();

        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Const.UPDATE_USER_IMAGE + User.getUserID(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show(); // Remove after testing
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                return prepareImage();
            }
        };

        queue.add(request);
    }

    public void getUsersImage(int userId) {
        String TAG = ProfileActivity.class.getSimpleName();

        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        ImageRequest request = new ImageRequest(Const.GET_USER_IMAGE + "" + userId, new Response.Listener<Bitmap>()
        {
            @Override
            public void onResponse(Bitmap bitmap) {
                profile_image.setImageBitmap(bitmap);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener()
                {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(request);
    }
}
*/