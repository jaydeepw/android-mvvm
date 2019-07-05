package com.github.jaydeepw.matchfilter;

import android.content.Intent;
import androidx.test.rule.ActivityTestRule;
import com.github.jaydeepw.matchfilter.views.MainActivity;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.github.jaydeepw.matchfilter.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class,false,false);

    @Test
    public void isAbleToLoadList() {
        activityRule.launchActivity(new Intent());

        onView(withId(R.id.matchesRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.messageTextView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.matchesRecyclerView)).check(withItemCount(greaterThan(1)));
    }

    @Test
    public void showFilterDialog() {
        activityRule.launchActivity(new Intent());

        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.buttonApply)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()));
    }
}
