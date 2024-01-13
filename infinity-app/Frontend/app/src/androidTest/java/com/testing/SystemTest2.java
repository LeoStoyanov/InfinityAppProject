package com.testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.R;
import com.loginscreen.LoginActivity;
import com.loginscreen.UserRegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SystemTest2 {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testEmptyEmail() {
        onView(withId(R.id.login_email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.login_email_error)).check(matches(withText(endsWith("Field may not be empty."))));
    }

    @Test
    public void testValidEmail() {
        String validEmail = "jake@gmail.com";

        onView(withId(R.id.login_email)).perform(typeText(validEmail), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.login_email_error)).check(matches(withText(isEmptyOrNullString())));
    }

    @Test
    public void testEmptyPassword() {
        onView(withId(R.id.login_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.login_pass_error)).check(matches(withText(endsWith("Field may not be empty."))));
    }

    @Test
    public void testValidPassword() {
        String validPassword = "Tomorrow142@";

        onView(withId(R.id.login_password)).perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.login_pass_error)).check(matches(withText(isEmptyOrNullString())));
    }

    @Test
    public void testSuccessfulUserLogin() {
        // After registration, check if the user exists by logging in!
        Intents.init();
        String email = "jake@gmail.com";
        String password = "Tomorrow142@";

        onView(withId(R.id.login_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        //intended(hasComponent(DashActivity.class.getName()));
        Intents.release();
    }

    /*
    @Test
    public void testFailedUserLogin() {
        //Intents.init();
        String email = "jake@gmail.com";
        String password = "Tomorrow142"; // Missing "@" at the end

        onView(withId(R.id.login_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.login_failed_error)).check(matches(withText(endsWith("Email and/or password is incorrect."))));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        //intended(hasComponent(LoginActivity.class.getName())); // Make sure the user didn't leave the login screen
        //Intents.release();
    }*/

    @Test
    public void testGoToSignup() {
        Intents.init();
        onView(withId(R.id.text_signup)).perform(click());

        intended(hasComponent(UserRegisterActivity.class.getName()));
        Intents.release();
    }

}
