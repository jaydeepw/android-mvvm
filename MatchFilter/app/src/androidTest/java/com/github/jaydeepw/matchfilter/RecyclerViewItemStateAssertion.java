package com.github.jaydeepw.matchfilter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import com.github.jaydeepw.matchfilter.models.entities.Match;
import com.github.jaydeepw.matchfilter.views.adapters.MatchesAdapter;
import org.hamcrest.Matcher;

import java.util.List;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

public class RecyclerViewItemStateAssertion implements ViewAssertion {
    private final Matcher<Boolean> favouritesMatcher;

    public static RecyclerViewItemStateAssertion hasFavourite(boolean fav) {
        return hasFavourite(is(fav));
    }

    public static RecyclerViewItemStateAssertion hasFavourite(Matcher<Boolean> matcher) {
        return new RecyclerViewItemStateAssertion(matcher);
    }

    private RecyclerViewItemStateAssertion(Matcher<Boolean> favouritesMatcher) {
        this.favouritesMatcher = favouritesMatcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        MatchesAdapter adapter = (MatchesAdapter) recyclerView.getAdapter();

        if (adapter != null) {
            List matches = adapter.getItems();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (matches != null) {
                    Match match = (Match) matches.get(i);
                    assertThat(match.getFavourite(), favouritesMatcher);
                    assertNotNull(match.getFavourite());
                }
            }
        }
    }
}
