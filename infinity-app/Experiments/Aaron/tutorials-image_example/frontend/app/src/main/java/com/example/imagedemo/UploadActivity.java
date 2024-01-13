package com.example.imagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    EditText etQuestion;
    EditText etAnswer;
    Button selectBtn;
    Button backBtn;
    Button uploadBtn;
    ImageView mImageView;

    // replace this with the actual address
    // 10.0.2.2 to be used for localhost if running springboot on the same host
    private static String upload_url = "http:10.0.2.2:8080/trivia/postwithimage";

    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mImageView = findViewById(R.id.imageSelView);
        etQuestion = findViewById(R.id.etQuestion);
        etAnswer = findViewById(R.id.etAnswer);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        selectBtn = findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // select picture from gallery
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);

            }
        });

        uploadBtn = findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTrivia();
            }
        });
    }

    /**
     * Thi is the callback function for image selecting from gallery
     * @param reqCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        // how you would get the image variable
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                mImageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * put together the post body
     * @return
     */
    private Map<String, String> prepareTrivia(){

        Map<String, String> params = new HashMap<String, String>();

        params.put("question", etQuestion.getText().toString());
        params.put("answer", etAnswer.getText().toString());

        // image to be posted as base64 string
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
            Toast.makeText(getApplicationContext(), "Error converting",Toast.LENGTH_LONG).show();
        }

        return params;
    }

    /**
     * volley to upload Trivia including [Question], [Answer], [Image]
     */
    private void uploadTrivia(){

        RequestQueue queue = Volley.newRequestQueue(UploadActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, upload_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // method to handle errors.
                        Toast.makeText(UploadActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                return prepareTrivia();
            }
        };

        queue.add(request);
    }
}