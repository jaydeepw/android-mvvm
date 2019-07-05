package com.github.jaydeepw.matchfilter;

import android.content.Intent;
import androidx.test.rule.ActivityTestRule;
import com.github.jaydeepw.matchfilter.views.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.github.jaydeepw.matchfilter.RecyclerViewItemCountAssertion.withItemCount;
import static com.github.jaydeepw.matchfilter.RecyclerViewItemStateAssertion.hasFavourite;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class,false,false);

    @Before
    public void launchApp() {
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void isAbleToLoadList() {
        onView(withId(R.id.matchesRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.messageTextView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.matchesRecyclerView)).check(withItemCount(greaterThan(1)));
    }

    @Test
    public void showFilterDialog() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.buttonApply)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()));
    }

    @Test
    public void filterDialogIsCancellable() {
        // given
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.buttonApply)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()));

        // when
        onView(withId(R.id.buttonCancel)).perform(click());

        // then
        // Dialog is gone and recyclerview is back
        onView(withId(R.id.matchesRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void checkboxesAreVisible() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.buttonApply)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()));

        onView(withId(R.id.checkboxHasPhotos)).check(matches(isDisplayed()));
        onView(withId(R.id.checkboxFavourites)).check(matches(isDisplayed()));
    }

    @Test
    public void checkboxesAreClickable() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.buttonApply)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()));

        // clickable again and again
        onView(withId(R.id.checkboxHasPhotos)).perform(click());
        onView(withId(R.id.checkboxHasPhotos)).perform(click());

        // clickable again and again
        onView(withId(R.id.checkboxFavourites)).perform(click());
        onView(withId(R.id.checkboxFavourites)).perform(click());
    }

    @Test
    public void filterByFavourite() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.buttonApply)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()));

        onView(withId(R.id.checkboxFavourites)).check(matches(isDisplayed()));
        onView(withId(R.id.checkboxFavourites)).perform(click());

        onView(withId(R.id.buttonApply)).perform(click());
        onView(withId(R.id.matchesRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.matchesRecyclerView)).check(hasFavourite(true));
    }
}
