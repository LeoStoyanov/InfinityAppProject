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
public class SystemTest1 {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityTestRule<UserRegisterActivity> activityRule = new ActivityTestRule<>(UserRegisterActivity.class);

    @Test
    public void testEmptyDisplayName() {
        onView(withId(R.id.reg_displayname)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_name_error)).check(matches(withText(endsWith("Field may not be empty."))));
    }

    @Test
    public void testIllegalCharDisplayName() {
        String illegalCharDisplayName = "Jake Jacobian!";

        onView(withId(R.id.reg_displayname)).perform(typeText(illegalCharDisplayName), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_name_error)).check(matches(withText(endsWith("Illegal character used: \"!\""))));
    }

    @Test
    public void testLongDisplayName() {
        String longDisplayName = "Jake Jacobian Johnson";

        onView(withId(R.id.reg_displayname)).perform(typeText(longDisplayName), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_name_error)).check(matches(withText(endsWith("Must be less than 16 characters."))));
    }

    @Test
    public void testShortDisplayName() {
        String shortDisplayName = "Jak";

        onView(withId(R.id.reg_displayname)).perform(typeText(shortDisplayName), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_name_error)).check(matches(withText(endsWith("Must be more than 3 characters."))));
    }

    @Test
    public void testValidDisplayName() {
        String validDisplayName = "Jake Jacobian";

        onView(withId(R.id.reg_displayname)).perform(typeText(validDisplayName), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_name_error)).check(matches(withText(isEmptyOrNullString())));
    }

    @Test
    public void testEmptyEmail() {
        onView(withId(R.id.login_email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_email_error)).check(matches(withText(endsWith("Field may not be empty."))));
    }

    @Test
    public void testInvalidEmail() {
        String invalidEmail = "jakeemail.com";

        onView(withId(R.id.login_email)).perform(typeText(invalidEmail), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_email_error)).check(matches(withText(endsWith("Invalid email."))));
    }

    @Test
    public void testValidEmail() {
        String validEmail = "jake@gmail.com";

        onView(withId(R.id.login_email)).perform(typeText(validEmail), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_email_error)).check(matches(withText(isEmptyOrNullString())));
    }

    @Test
    public void testEmptyPassword() {
        onView(withId(R.id.login_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_pass_error)).check(matches(withText(endsWith("Field may not be empty."))));
    }

    @Test
    public void testIllegalCharPassword() {
        String illegalCharPassword = "Tomorrow142~";

        onView(withId(R.id.login_password)).perform(typeText(illegalCharPassword), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_pass_error)).check(matches(withText(endsWith("Illegal character used: \"~\""))));
    }

    @Test
    public void testLongPassword() {
        String longPassword = "Tomorrow1422345790";

        onView(withId(R.id.login_password)).perform(typeText(longPassword), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_pass_error)).check(matches(withText(endsWith("Must be less than 16 characters."))));
    }

    @Test
    public void testShortPassword() {
        String shortPassword = "Tomorr";

        onView(withId(R.id.login_password)).perform(typeText(shortPassword), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_pass_error)).check(matches(withText(endsWith("Must be more than 8 characters."))));
    }

    @Test
    public void testNoUppercasePassword() {
        String noUppercasePassword = "tomorrow142@";

        onView(withId(R.id.login_password)).perform(typeText(noUppercasePassword), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_pass_error)).check(matches(withText(endsWith("Must have at least one uppercase letter."))));
    }

    @Test
    public void testValidPassword() {
        String validPassword = "Tomorrow142@";

        onView(withId(R.id.login_password)).perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_pass_error)).check(matches(withText(isEmptyOrNullString())));
    }

    @Test
    public void testFalseMatchingPassword() {
        String password = "Tomorrow142@";
        String conPassword = "Tomorow142@";

        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.reg_con_password)).perform(typeText(conPassword), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());
        onView(withId(R.id.reg_confirm_pass_error)).check(matches(withText(endsWith("Passwords do not match."))));
    }


    @Test
    public void testUserCreation() {
        Intents.init();
        String displayName = "Danny Jacobian";
        String email = "danny@gmail.com";
        String password = "Tomorrow145@";

        onView(withId(R.id.reg_displayname)).perform(typeText(displayName), closeSoftKeyboard());
        onView(withId(R.id.login_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.reg_con_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        intended(hasComponent(LoginActivity.class.getName())); // Make sure the user didn't leave the login screen
        Intents.release();

    }

    @Test
    public void testFailedUserCreation() {
        String displayName = "Jake Jacobian";
        String email = "jake@gmail.com";
        String password = "Tomorrow142@";

        onView(withId(R.id.reg_displayname)).perform(typeText(displayName), closeSoftKeyboard());
        onView(withId(R.id.login_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.reg_con_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.reg_button)).perform(click());

        onView(withId(R.id.reg_email_exists_error)).check(matches(withText(endsWith("Email is already registered to an account."))));
    }

    @Test
    public void testAccountAlreadyExists() {
        Intents.init();
        onView(withId(R.id.text_account_exists)).perform(click());

        intended(hasComponent(LoginActivity.class.getName()));
        Intents.release();
    }

}
