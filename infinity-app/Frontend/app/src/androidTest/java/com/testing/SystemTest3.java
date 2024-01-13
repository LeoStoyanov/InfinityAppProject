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
import com.dashboard.DashActivity;
import com.friends.FindFriends;
import com.friends.FriendsList;
import com.friends.PendingFriendList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SystemTest3 {
    private static final int SIMULATED_DELAY_MS = 2000;

    @Rule
    public ActivityTestRule<FriendsList> activityRule =
            new ActivityTestRule<>(FriendsList.class);

    @Test
    public void testGoHome() {
        Intents.init();
        onView(withId(R.id.home_button)).perform(click());

        intended(hasComponent(DashActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testGoToPendingFriendsList() {
        Intents.init();
        onView(withId(R.id.pfriends_list_button)).perform(click());

        intended(hasComponent(PendingFriendList.class.getName()));
        Intents.release();
    }

    @Test
    public void testGoToFindFriendsList() {
        Intents.init();
        onView(withId(R.id.ffriends_list_button)).perform(click());

        intended(hasComponent(FindFriends.class.getName()));
        Intents.release();
    }

    @Test
    public void testFriendExists() {
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onData(anything())
                .inAdapterView(withId(R.id.list_friends))
                .atPosition(0)
                .onChildView(withId(R.id.friend_name)).check(matches(withText(endsWith("Emily Emo"))));
        onData(anything())
                .inAdapterView(withId(R.id.list_friends))
                .atPosition(0)
                .onChildView(withId(R.id.friend_username)).check(matches(withText(endsWith("Emily Emo#4"))));
    }
}
