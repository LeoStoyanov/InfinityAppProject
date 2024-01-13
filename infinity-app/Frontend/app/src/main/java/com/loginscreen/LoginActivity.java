package com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.app.User;
import com.dashboard.DashActivity;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * @author Leo Stoyanov
 */
/**
 * View class of the login action of the user.
 */
public class LoginActivity extends AppCompatActivity implements IView {

    private static TextView email;
    private static TextView password;
    private static TextView emailError;
    private static TextView passError;
    private static TextView loginFailedError;
    private static Button login;
    private static IPresenter presenter;

    /**
     * Loads the activity_login xml file, assigns field variables to the components
     * of the layout, and creates OnClickListeners to check password validity, check email validity,
     * and send a GET request to check if the user exists in the database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView button_signup = findViewById(R.id.text_signup);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        emailError = findViewById(R.id.login_email_error);
        passError = findViewById(R.id.login_pass_error);
        loginFailedError = findViewById(R.id.login_failed_error);
        login = findViewById(R.id.button_login);

        presenter = new LoginActivityPresenter(this, getApplicationContext());

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), UserRegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean emailValid = isEmailValid();
                boolean passwordValid = isPasswordValid();
                if (emailValid && passwordValid) {
                    presenter.loadMessage(Const.USER_LOGIN_INFO, "get");
                }
            }
        });
    }

    /**
     * Checks the input email based upon an email validity method in the UserRegisterActivity class.
     * If the email is valid, return true, otherwise false.
     * @return returns boolean whether the email is valid
     */
    private boolean isEmailValid() {
        String emailInputed = email.getText().toString();
        if (emailInputed.isEmpty()) {
            emailError.setText("Field may not be empty.");
            return false;
        }
        emailError.setText(null);
        return true;
    }

    /**
     * Checks whether the user's input password is empty. If it is not empty, return true, otherwise false.
     * @return returns boolean whether the password is valid
     */
    private boolean isPasswordValid() {
        String passwordInputed = password.getText().toString();
        if (passwordInputed.isEmpty()) {
            passError.setText("Field may not be empty.");
            return false;
        }
        passError.setText(null);
        return true;
    }

    /**
     * Logs in the user based upon his or her credentials (i.e., email and password) and starts
     * the DashActivity in order to open the Dashboard for the user. If the user's credentials are invalid,
     * notify him or her with an error.
     * @param validation
     * @param id
     */
    private void allowUserLogin(Boolean validation, int id) {
        if (validation) {
            System.out.println("User's email and password match the database, proceed with login!");
            loginFailedError.setText(null);
            startActivity(new Intent(LoginActivity.this, DashActivity.class));
            User.setUserID("" + id);
        }
        else {
            loginFailedError.setText("Email and/or password is incorrect.");
        }

    }

    /**
     * Loops through the passed JSONArray from a GET Request. If a user's email and password match an email and password, respectively,
     * in a JSONObject, then set the boolean of the found variable to true. If neither email nor password match any respective email and
     * password, then keep the boolean of the found variable false. This found variable determines whether the user can log in
     * inside of the allowUserLogin method.
     * @param result JSONArray reponse from a GET request
     */
    @Override
    public void useResponse(JSONArray result) {
        final boolean[] found = {false}; // Android Studio forced me to make this boolean variable a boolean array
        String emailInputed = email.getText().toString();
        String passwordInputed = password.getText().toString();
        final int[] idFound = {-1};
        int loginID = -1;
        try {
            for (int i = 0; i < result.length(); ++i) {
                JSONObject user = result.getJSONObject(i);
                idFound[0] = user.getInt("id");
                String displayName = user.getString("displayname");
                String emailFound = user.getString("email");
                String passFound = user.getString("password");
                int imageID = 0; // TODO: Image grabbing must be implemented!

                if (emailFound.equals(emailInputed) && passFound.equals(passwordInputed)) {
                    loginID = idFound[0];
                    found[0] = true;
                }
            }
            allowUserLogin(found[0], loginID);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}