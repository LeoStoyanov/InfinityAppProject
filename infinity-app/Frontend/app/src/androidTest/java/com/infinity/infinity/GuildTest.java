package com.infinity.infinity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.R;
import com.guild.GuildActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GuildTest {

    @Rule
    public ActivityTestRule<GuildActivity> activityRule = new ActivityTestRule<>(GuildActivity.class);

    @Test
    public void messageTest(){
        onView(withId(R.id.message_edit)).perform(typeText("test message"));
        onView(withId(R.id.send_button)).perform(click());
    }

    @Test
    public void buttonsTest(){

        onView(withId(R.id.notes_button)).perform(click());
        pressBack();

        onView(withId(R.id.drawings_button)).perform(click());
        pressBack();

        onView(withId(R.id.popup_button)).perform(click());
    }

}
