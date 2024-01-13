package com.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.*;
import com.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * @author Leo Stoyanov
 */
/**
 * View class of the registration of the user to join the Infinity application.
 */
public class UserRegisterActivity extends AppCompatActivity implements IView {

    private static TextView displayName;
    private static TextView email;
    private static TextView password;
    private static TextView confirm_pass;
    private static TextView account_exists;
    private static Button register;
    private static TextView nameError;
    private static TextView emailError;
    private static TextView passError;
    private static TextView conpassError;
    private static TextView emailExistsError;
    private static char[] illegalCharName = {'!', '~', '[', ']', '{', '}', ';', ':', '\'', '"', ',', '/', '?'};
    private static char[] illegalCharPassword = {'~', '[', ']', '{', '}', ';', ':', '\'', '"', ',', '/', '?', ' '};
    private static IPresenter presenter;

    /**
     * Loads the activity_user_register xml file, assigns field variables to the components
     * of the layout, and creates two OnClickListeners.
     *
     * (1) register.OnClickListener: If the user clicks to register, check display name validity, email validity,
     * password validity, and if password and confirm password match. If all are valid, then
     * send a POST request for the user to register.
     * (2) account_exists.OnClickListener: If the user has an account, he or she can click to
     * return to the login screen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        displayName = findViewById(R.id.reg_displayname);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        confirm_pass = findViewById(R.id.reg_con_password);
        account_exists = findViewById(R.id.text_account_exists);
        register = findViewById(R.id.reg_button);
        nameError = findViewById(R.id.reg_name_error);
        emailError = findViewById(R.id.reg_email_error);
        passError = findViewById(R.id.reg_pass_error);
        conpassError = findViewById(R.id.reg_confirm_pass_error);
        emailExistsError = findViewById(R.id.reg_email_exists_error);
        UserRegisterActivity viewClass = this;

        account_exists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter = new UserRegisterPresenter(viewClass, getApplicationContext(), displayName.getText().toString(), email.getText().toString(), password.getText().toString());

                boolean nameValid = isDisplayNameValid();
                boolean emailValid = isEmailValid();
                boolean passValid = isPasswordValid(password, passError);
                boolean passMatch = doPasswordsMatch(password, confirm_pass, conpassError);
                if (nameValid && emailValid && passValid && passMatch) {
                    presenter.loadMessage(Const.REGISTER_CREATE_USER, "get");
                }
            }
        });
    }

    /**
     * Checks the user's input display name. If it is not empty, does not contain any illegal
     * characters, is not greater than 16 characters, and is not less than 4 characters, then
     * return true, otherwise false.
     * @return returns boolean whether the user's input display name is valid
     */
    private boolean isDisplayNameValid() {
        String displayNameInputed = displayName.getText().toString();
        if (displayNameInputed.isEmpty()) {
            nameError.setText("Field may not be empty.");
            return false;
        }
        else {
            for (int i = 0; i < displayNameInputed.length(); ++i) {
                char char_name = displayNameInputed.charAt(i);
                for (int j = 0; j < illegalCharName.length; ++j) {
                    if (char_name == illegalCharName[j]) {
                        // Potentially add a table to cache ALL illegal characters found, which then can be displayed to the user at once.
                        nameError.setText("Illegal character used: \"" + char_name + "\"");
                        return false;
                    }

                }
            }
            if (displayNameInputed.length() > 16) {
                nameError.setText("Must be less than 16 characters.");
                return false;
            }
            else if (displayNameInputed.length() < 4) {
                nameError.setText("Must be more than 3 characters.");
                return false;
            }
        }
        nameError.setText(null);
        return true;
    }

    /**
     * Checks the user's input email. If the email is not empty and matches email patterns
     * based on an Android library, then return true, otherwise false.
     * @return returns boolean whether the user's input email is valid
     */
    private boolean isEmailValid() {
        String emailInputed = email.getText().toString();
        if (emailInputed.isEmpty()) {
            emailError.setText("Field may not be empty.");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInputed).matches()) {
            emailError.setText("Invalid email.");
            return false;
        }
        emailError.setText(null);
        return true;
    }

    /**
     * Checks the input password of the user to ensure it is not empty, does not contain
     * illegal characters, does not exceed 16 characters, is not less than 8 characters, and
     * that it contains an uppercase letter. If the password meets these requirements,
     * return true, otherwise false.
     * @param pass user's input password
     * @param error error message if the input password is invalid
     * @return boolean whether the user's input password is valid
     */
    public static boolean isPasswordValid(TextView pass, TextView error) {
        String passwordInputed = pass.getText().toString();
        Boolean foundUppercase = false;
        if (passwordInputed.isEmpty()) {
            error.setText("Field may not be empty.");
            return false;
        }
        else {
            for (int i = 0; i < passwordInputed.length(); ++i) {
                char char_name = passwordInputed.charAt(i);
                for (int j = 0; j < illegalCharPassword.length; ++j) {
                    if (char_name == illegalCharPassword[j]) { // Special case: check for spaces for passwords
                        error.setText("Illegal character used: \"" + char_name + "\"");
                        return false;
                    }
                    if (Character.isUpperCase(char_name)) {
                        foundUppercase = true;
                    }

                }
            }
            if (passwordInputed.length() > 16) {
                error.setText("Must be less than 16 characters.");
                return false;
            }
            else if (passwordInputed.length() < 8) {
                error.setText("Must be more than 8 characters.");
                return false;
            }
            else if (foundUppercase == false) {
                error.setText("Must have at least one uppercase letter.");
                return false;
            }

        }
        error.setText(null);
        return true;
    }

    /**
     * Checks the input password and input confirm password to determine if their characters
     * are equal. If the characters are equal, then return true, otherwise false.
     * @param pass user's input password
     * @param conPass user's input confirm password
     * @param error error message the passwords do not match
     * @return boolean whether the user's input password and input confirm password match
     */
    public static boolean doPasswordsMatch(TextView pass, TextView conPass, TextView error) {
        String passwordInputed = pass.getText().toString();
        String conPasswordInputed = conPass.getText().toString();
        if (!passwordInputed.equals(conPasswordInputed)) {
            error.setText("Passwords do not match.");
            return false;
        }
        error.setText(null);
        return true;
    }

    /**
     * If the user's email already exists within the database, then display an error
     * that the email entered is already registered to an account. If the user's email
     * does not exist within the database, then send a POST request to save the user's
     * entered credentials to the database and send the user back to the log in screen.
     * @param emailExists
     */
    private void allowUserRegister(boolean emailExists) {
        if (emailExists) {
            emailExistsError.setText("Email is already registered to an account.");
        }
        else {
            emailExistsError.setText(null);
            presenter.loadMessage(Const.REGISTER_CREATE_USER, "post"); // Send new user's information to the server
            startActivity(new Intent(UserRegisterActivity.this, LoginActivity.class)); // Send user to the login screen to now login
        }
    }

    /**
     * Loops through the returned JSONArray from a GET Request. If a user's email matches an email
     * in a JSONObject, then set the boolean of the found variable to true. If the input email does
     * not match any email, then keep the boolean of the found variable false. This found variable
     * determines whether the user can register inside of the allowUserRegister method.
     * @param result JSONArray reponse from a GET request
     */
    @Override
    public void useResponse(JSONArray result) {
        final boolean[] found = {false}; // Android Studio forced me to make this boolean variable a boolean array
        String emailInputed = email.getText().toString();
        try {
            for (int i = 0; i < result.length(); ++i) {
                JSONObject user = result.getJSONObject(i);
                String emailFound = user.getString("email");

                if (emailFound.equals(emailInputed)) {
                    found[0] = true;
                    break;
                }
            }
            allowUserRegister(found[0]);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}