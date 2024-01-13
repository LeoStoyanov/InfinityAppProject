package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void disable(View v)
    {
       View view = findViewById(R.id.button3);
       view.setEnabled(false);
       Button button = (Button) view;
       button.setText("Disabled");
       //below is the same thing just written differently
//        v.setEnabled(false);
//        Button b = (Button) v;
//        b.setText("Disabled");
//        Log.d("success", "Button Disabled");
    }
    public void handleText(View v)
    {
        EditText t = findViewById(R.id.source);
        String input = t.getText().toString();
        ((TextView)findViewById(R.id.copyoutput)).setText(input);
        //makes an alert
        Toast.makeText(this, "Alert: I made this", Toast.LENGTH_LONG).show();

        Log.d("info",input);
    }


}