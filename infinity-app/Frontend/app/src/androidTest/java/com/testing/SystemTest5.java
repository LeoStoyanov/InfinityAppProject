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
import com.friends.FriendsList;
import com.friends.PendingFriendList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SystemTest5 {
    private static final int SIMULATED_DELAY_MS = 2000;

    @Rule
    public ActivityTestRule<PendingFriendList> activityRule =
            new ActivityTestRule<>(PendingFriendList.class);

    @Test
    public void testGoBack() {
        Intents.init();
        onView(withId(R.id.back_pfriends)).perform(click());

        intended(hasComponent(FriendsList.class.getName()));
        Intents.release();
    }

    @Test
    public void testPendingFriendExists() {
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onData(anything())
                .inAdapterView(withId(R.id.list_pendingfriends))
                .atPosition(0)
                .onChildView(withId(R.id.friend_name)).check(matches(withText(endsWith("Sarah Saturn"))));
        onData(anything())
                .inAdapterView(withId(R.id.list_pendingfriends))
                .atPosition(0)
                .onChildView(withId(R.id.friend_username)).check(matches(withText(endsWith("Sarah Saturn#2"))));
    }
}
