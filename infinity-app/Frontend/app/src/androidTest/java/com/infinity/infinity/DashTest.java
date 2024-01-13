package com.infinity.infinity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.R;
import com.dashboard.DashActivity;
import com.dashboard.PrivateListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DashTest {

    @Rule
    public ActivityTestRule<DashActivity> activityRule = new ActivityTestRule<>(DashActivity.class);

    @Rule
    public ActivityTestRule<PrivateListActivity> privateRule = new ActivityTestRule<>(PrivateListActivity.class);

    @Test
    public void dashTest() {

        onView(withId(R.id.profile_button)).perform(click());
        onView(withId(R.id.home_button)).perform(click());

        onView(withId(R.id.private_button)).perform(click());
        onView(withId(R.id.home_button)).perform(click());

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
        }
        //if(getRVCount() > 0)
        onView(withId(R.id.server_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.home_button)).perform(click());

        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
        }
        onView(withId(R.id.friends_button)).perform(click());
    }

    private int getRVCount(){
        RecyclerView recyclerView = (RecyclerView) activityRule.getActivity().findViewById(R.id.server_recyclerview);
        return recyclerView.getAdapter().getItemCount();
    }

}