package com.example.imagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    EditText etURL;
    Button getBtn;
    Button uploadBtn;
    ImageView mImageView;

    // replace this with the actual address
    // 10.0.2.2 to be used for localhost if running springboot on the same host
    private String url = "http://10.0.2.2:8080/trivia/image/X";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageView);
        etURL = findViewById(R.id.etUrl);
        etURL.setText(url);

        getBtn = findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTriviaImage();
            }
        });

        uploadBtn = findViewById(R.id.toUploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getTriviaImage()
    {
        // read in URL
        url = etURL.getText().toString();
        if(TextUtils.isEmpty(url)) {
            Toast.makeText(getApplicationContext(),"enter url!",Toast.LENGTH_LONG).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>()
                {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
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