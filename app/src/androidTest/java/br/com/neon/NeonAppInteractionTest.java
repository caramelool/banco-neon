package br.com.neon;

import static android.support.test.espresso.Espresso.*;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import static android.support.test.espresso.action.ViewActions.*;

import static android.support.test.espresso.contrib.RecyclerViewActions.*;

import static android.support.test.espresso.assertion.ViewAssertions.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.neon.ui.profile.ProfileActivity;

@RunWith(AndroidJUnit4.class)
public class NeonAppInteractionTest {

    @Rule
    public ActivityTestRule<ProfileActivity> activityTestRule =
            new ActivityTestRule<>(ProfileActivity.class);

    @Before
    public void before() {
    }

    @Test
    public void test_happy_interaction() {
        sleep(1000);
        onView(withId(R.id.try_again_button))
                .check(matches(isClickable()));

        sleep(500);
        onView(withId(R.id.send_money_button))
                .perform(click());

        sleep(500);
        onView(withId(R.id.recycler_view)).perform(
                actionOnItemAtPosition(1, click()));

        sleep(500);
        onView(withId(R.id.close_image_button))
                .perform(click());

        sleep(500);
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(3, click()));

        sleep(500);
        onView(withId(R.id.send_button))
                .perform(click());

        sleep(500);
        onView(withText(R.string.message_transfer_success))
                .check(doesNotExist());

        onView(withText(R.string.message_transfer_fail))
                .check(matches(isDisplayed()));

        sleep(500);
        onView(withId(android.support.design.R.id.snackbar_action))
                .perform(click());

        sleep(500);
        onView(withId(R.id.recycler_view)).perform(
                scrollToPosition(14));

        sleep(500);
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(14, click()));

        sleep(500);
        onView(withId(R.id.value_edit_text))
                .perform(typeText("100.20"), closeSoftKeyboard());

        sleep(500);
        onView(withId(R.id.send_button))
                .perform(click());

        sleep(500);
        onView(withText(R.string.message_transfer_success))
                .check(matches(isDisplayed()));

        onView(withText(R.string.message_transfer_fail))
                .check(doesNotExist());

        sleep(500);
        onView(withId(android.support.design.R.id.snackbar_action))
                .perform(click());

        sleep(500);
        onView(withContentDescription("Navigate up"))
                .perform(click());

        sleep(500);
        onView(withId(R.id.send_history_button))
                .perform(click());

        sleep(500);
        onView(withId(R.id.history_graphic_recycler_view))
                .perform(scrollToPosition(14));

        sleep(500);
        onView(withId(R.id.history_graphic_recycler_view))
                .perform(scrollToPosition(0));

        sleep(500);
        onView(withId(R.id.history_recycler_view))
                .perform(scrollToPosition(14));

        sleep(500);
        onView(withId(R.id.history_recycler_view))
                .perform(scrollToPosition(0));

        sleep(500);
        onView(withContentDescription("Navigate up"))
                .perform(click());

        sleep(500);
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            //Do nothing
        }
    }

}
