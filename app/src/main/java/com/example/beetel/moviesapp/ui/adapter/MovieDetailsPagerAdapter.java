package com.example.beetel.moviesapp.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.beetel.moviesapp.data.model.MovieResultListModel;
import com.example.beetel.moviesapp.ui.fragments.DetailFragment;
import com.example.beetel.moviesapp.ui.fragments.ReviewsFragment;
import com.example.beetel.moviesapp.ui.fragments.TrailerFragment;
import com.example.beetel.moviesapp.util.MovieApiUtility;

/**
 * Created by beetel on 9/03/2016.
 */
public class MovieDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private MovieResultListModel.ResultModel resultModel;

    public MovieDetailsPagerAdapter(FragmentManager fm, MovieResultListModel.ResultModel model) {
        super(fm);
        this.resultModel = model;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DetailFragment detailActivityFragment = new DetailFragment();
                Bundle arg = new Bundle();
                arg.putParcelable(MovieApiUtility.EXTRA_DETAIL_FRAGMENT, resultModel);
                detailActivityFragment.setArguments(arg);
                return detailActivityFragment;

            case 1:
                TrailerFragment trailerFragment = new TrailerFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieApiUtility.EXTRA_TRAILER_FRAGMENT, resultModel);
                trailerFragment.setArguments(bundle);
                return trailerFragment;

            case 2:
                ReviewsFragment reviewsFragment = new ReviewsFragment();
                Bundle args = new Bundle();
                args.putParcelable(MovieApiUtility.EXTRA_REVIEW_FRAGMENT, resultModel);
                reviewsFragment.setArguments(args);
                return reviewsFragment;

            default:
                return new Fragment();

        }
    }

    @Override
    public int getCount() {
        return MovieApiUtility.TABS_MOVIE_DETAIL.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < MovieApiUtility.TABS_MOVIE_DETAIL.length) {
            return MovieApiUtility.TABS_MOVIE_DETAIL[position];
        }
        return null;
    }
}
