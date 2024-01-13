package com.testing;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.notes.NotesList.notes;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.R;
import com.dashboard.DashActivity;
import com.notes.NotesEditor;
import com.notes.NotesList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SystemTest4 {
    private static final int SIMULATED_DELAY_MS = 2000;

    @Rule
    public ActivityTestRule<NotesList> activityRule =
            new ActivityTestRule<>(NotesList.class);

    @Test
    public void testGoHome() {
        Intents.init();
        onView(withId(R.id.home_button)).perform(click());

        intended(hasComponent(DashActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testCreateNote() {
        Intents.init();
        onView(withId(R.id.new_note)).perform(click());

        intended(hasComponent(NotesEditor.class.getName()));
        Intents.release();
    }

    @Test
    public void testGoToNote() {
        if (notes.isEmpty()) {
            onView(withId(R.id.new_note)).perform(click());
            onView(withId(R.id.back_note)).perform(click());
        }
        Intents.init();
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onData(anything())
                .inAdapterView(withId(R.id.notes_list))
                .atPosition(0)
                .perform(click());

        intended(hasComponent(NotesEditor.class.getName()));
        Intents.release();
    }

    @Test
    public void testDeleteNote() {
        Intents.init();
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onData(anything())
                .inAdapterView(withId(R.id.notes_list))
                .atPosition(0)
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        onView(withText("Yes"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        Intents.release();
    }

}
