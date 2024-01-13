package com.example.exp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button button_zero;
    private Button button_one;
    private Button button_two;
    private Button button_three;
    private Button button_four;
    private Button button_five;
    private Button button_six;
    private Button button_seven;
    private Button button_eight;
    private Button button_nine;
    private Button button_add;
    private Button button_sub;
    private Button button_mul;
    private Button button_div;
    private Button button_equal;
    private Button button_clear;
    private TextView text_info;
    private TextView text_result;
    private final char ADDITION = '+';
    private final char SUBTRACTION = '-';
    private final char MULTIPLICATION = '*';
    private final char DIVISION = '/';
    private final char EQUAL = '=';
    private double value1 = Double.NaN;
    private double value2 = 0;
    private char act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();

        button_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "0");
            }
        });

        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "1");
            }
        });

        button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "2");
            }
        });

        button_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "3");
            }
        });

        button_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "4");
            }
        });

        button_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "5");
            }
        });

        button_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "6");
            }
        });

        button_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "7");
            }
        });

        button_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "8");
            }
        });

        button_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_info.setText(text_info.getText().toString() + "9");
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computation();
                act = ADDITION;
                text_result.setText(String.valueOf(value1) + "+");
                text_info.setText(null);
            }
        });

        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computation();
                act = SUBTRACTION;
                text_result.setText(String.valueOf(value1) + "-");
                text_info.setText(null);
            }
        });

        button_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computation();
                act = MULTIPLICATION;
                text_result.setText(String.valueOf(value1) + "-");
                text_info.setText(null);
            }
        });

        button_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computation();
                act = DIVISION;
                text_result.setText(String.valueOf(value1) + "/");
                text_info.setText(null);
            }
        });

        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computation();
                act = EQUAL;
                // text_result stores our initial value1 and the operator; our current value1 is the calculation's result
                text_result.setText(text_result.getText().toString() + String.valueOf(value2) + "=" + String.valueOf(value1));
                text_info.setText(null);
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_info.getText().length() > 0) {
                    // Allows to remove char one by one
                    CharSequence name = text_info.getText().toString();
                    text_info.setText(name.subSequence(0, name.length() - 1));
                }
                else {
                    value1 = Double.NaN;
                    value2 = Double.NaN;
                    text_info.setText(null);
                    text_result.setText(null);
                }
            }
        });

    }

    private void computation() {
        if (!Double.isNaN(value1)) { // Check if the first value is a number; this will be false initially, thus
                                     // indicating that the user must first pick a value (i.e., value1)
            value2 = Double.parseDouble(text_info.getText().toString());

            switch(act) {
                case ADDITION:
                    value1 += value2;
                    break;
                case SUBTRACTION:
                    value1 -= value2;
                    break;
                case MULTIPLICATION:
                    value1 *= value2;
                    break;
                case DIVISION:
                    value1 /= value2;
                    break;
                case EQUAL:
                    break;
            }
        }
        else {
            value1 = Double.parseDouble(text_info.getText().toString());
        }
    }

    private void setUpViews() {
        button_zero = findViewById(R.id.button0);
        button_one = findViewById(R.id.button1);
        button_two = findViewById(R.id.button2);
        button_three = findViewById(R.id.button3);
        button_four = findViewById(R.id.button4);
        button_five = findViewById(R.id.button5);
        button_six = findViewById(R.id.button6);
        button_seven = findViewById(R.id.button7);
        button_eight = findViewById(R.id.button8);
        button_nine = findViewById(R.id.button9);
        button_add = findViewById(R.id.button_add);
        button_sub = findViewById(R.id.button_subtract);
        button_mul = findViewById(R.id.button_multiply);
        button_div = findViewById(R.id.button_divide);
        button_equal = findViewById(R.id.button_equal);
        button_clear = findViewById(R.id.button_clear);
        text_info = findViewById(R.id.textView_control);
        text_result = findViewById(R.id.textView_display);
    }






















}