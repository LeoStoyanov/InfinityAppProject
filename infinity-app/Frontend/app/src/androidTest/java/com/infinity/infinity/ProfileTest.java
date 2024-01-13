package com.infinity.infinity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.R;
import com.profile.ProfileActivity;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ProfileTest {

    @Rule
    public ActivityTestRule<ProfileActivity> profileRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void profileTest() {
        newPasswordTest();
        newPFPTest();
    }

    private void newPFPTest(){
        onView(withId(R.id.edit_pfp_button)).perform(click());
        pressBack();
    }

    public void newPasswordTest(){
        //Passwords don't match
        onView(withId(R.id.change_pw_button)).perform(click());
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
        }
        onView(withId(R.id.first_password)).perform(typeText("wrong spelling"));
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
        }
        onView(withId(R.id.link_edit)).perform(typeText("wrong"));
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
        }
        onView(withId(R.id.apply_button)).perform(click());

        //Passwords DO match
        onView(withId(R.id.link_edit)).perform(typeText(" spelling"));
        onView(withId(R.id.apply_button)).perform(click());
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
        }

        //Cancel button works
        onView(withId(R.id.change_pw_button)).perform(click());
        onView(withId(R.id.cancel_button)).perform(click());
    }

}
