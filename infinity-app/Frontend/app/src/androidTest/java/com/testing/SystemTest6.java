package com.testing;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.endsWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.R;
import com.friends.FindFriends;
import com.friends.FriendsList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SystemTest6 {
    private static final int SIMULATED_DELAY_MS = 2000;

    @Rule
    public ActivityTestRule<FindFriends> activityRule =
            new ActivityTestRule<>(FindFriends.class);


    @Test
    public void testGoBack() {
        Intents.init();
        onView(withId(R.id.back_ffriends)).perform(click());

        intended(hasComponent(FriendsList.class.getName()));
        Intents.release();
    }

    @Test
    public void testPotentialFriendExists() {
        Intents.init();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.back_ffriends)).perform(click());
        onView(withId(R.id.ffriends_list_button)).perform(click());
        Intents.release();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        /*
        onData(anything())
                .inAdapterView(withId(R.id.list_pot_friends))
                .atPosition(0)
                .onChildView(withId(R.id.friend_name)).check(matches(withText(endsWith("Todd Turner"))));
        onData(anything())
                .inAdapterView(withId(R.id.list_pot_friends))
                .atPosition(0)
                .onChildView(withId(R.id.friend_username)).check(matches(withText(endsWith("Todd Turner#6"))));

         */
    }
}
