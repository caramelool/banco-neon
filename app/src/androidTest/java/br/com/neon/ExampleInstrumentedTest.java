package br.com.neon;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import static android.support.test.espresso.Espresso.*;

import static android.support.test.espresso.matcher.ViewMatchers.*;

import static android.support.test.espresso.assertion.ViewAssertions.*;

import static android.support.test.espresso.action.ViewActions.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.neon.ui.profile.ProfileActivity;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<ProfileActivity> activityTestRule =
            new ActivityTestRule<>(ProfileActivity.class);

    @Before
    public void before() {
//        Context context = InstrumentationRegistry.getTargetContext();
        activityTestRule.launchActivity(null);
    }

    @Test
    public void test_send_money() {
        onView(withId(R.id.send_money_button)).perform(click());
    }

    void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            //Do nothing
        }
    }

}
