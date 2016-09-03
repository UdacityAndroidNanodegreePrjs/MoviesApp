package com.example.beetel.moviesapp.ui.fragments;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.beetel.moviesapp.R;
import com.example.beetel.moviesapp.data.db.MovieContract;
import com.example.beetel.moviesapp.data.model.MovieResultListModel;
import com.example.beetel.moviesapp.util.MovieApiUtility;
import com.like.LikeButton;
import com.like.OnLikeListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    @Bind(R.id.imageposter)
    ImageView imgPoster;
    @Bind(R.id.releasedate)
    TextView tvReleaseDate;
    @Bind(R.id.rating)
    TextView tvRating;
    @Bind(R.id.plot)
    TextView tvPlot;
    @Bind(R.id.heart_button)
    LikeButton heartButton;
    MovieResultListModel.ResultModel model;

    private ContentValues values;


    public DetailFragment() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        getActivity().supportInvalidateOptionsMenu();
        model = getActivity().getIntent().getParcelableExtra(MovieApiUtility.EXTRA_RESULT_MODEL);

        if (model == null)
            model = this.getArguments().getParcelable(MovieApiUtility.EXTRA_DETAIL_FRAGMENT);

        String url = MovieApiUtility.URL_IMAGE_BASE + model.getPosterUrl();
        //  tvTitle.setText(model.getOriginalTitle());
        tvReleaseDate.setText(String.format(getString(R.string.release_date), model.getReleaseDate()));
        tvRating.setText(String.format(getString(R.string.rating), model.getRating()));
        tvPlot.setText(model.getPlot());
        Glide.with(getContext())
                .load(url)
                .error(R.drawable.poster)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPoster);

        values = new ContentValues();

        if (model.getPosterUrl() == null)
            model.setPosterUrl("No url found");
        if (model.getBackdropUrl() == null)
            model.setBackdropUrl("No url found");

        values.put(MovieContract.Movie.COLUMN_TITLE, model.getTitle());
        values.put(MovieContract.Movie.COLUMN_POSTER_URL, model.getPosterUrl());
        values.put(MovieContract.Movie.COLUMN_BACK_DROP_URL, model.getBackdropUrl());
        values.put(MovieContract.Movie.COLUMN_ORIGINAL_TITLE, model.getOriginalTitle());
        values.put(MovieContract.Movie.COLUMN_PLOT, model.getPlot());
        values.put(MovieContract.Movie.COLUMN_RATING, model.getRating());
        values.put(MovieContract.Movie.COLUMN_RELEASE_DATE, model.getReleaseDate());
        values.put(MovieContract.Movie.COLUMN_MOVIE_ID, model.getMovieId());

        Cursor c = getContext().getContentResolver().query(MovieContract.Movie.CONTENT_URI,
                new String[]{MovieContract.Movie.COLUMN_MOVIE_ID},
                MovieContract.Movie.COLUMN_MOVIE_ID + "= ? ",
                new String[]{model.getMovieId()},
                null);

        if (c.getCount() > 0) {
            heartButton.setLiked(true);
        }


        heartButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Uri rowUri;
                rowUri = getContext().getContentResolver().insert(MovieContract.Movie.CONTENT_URI, values);
                long rowId = ContentUris.parseId(rowUri);
                if (rowId > 0)
                    Toast.makeText(getContext(), "Favourite  " + model.getTitle(), Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "New row id inserted via provider: " + rowId);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                int rowDeleted = getContext().getContentResolver().delete(MovieContract.Movie.CONTENT_URI, MovieContract.Movie.COLUMN_MOVIE_ID + "= ?", new String[]{model.getMovieId()});
                if (rowDeleted > 0)
                    Toast.makeText(getContext(), "Removed  " + model.getTitle() + " from favourite", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "Row deleted: " + rowDeleted);
            }
        });

        return view;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }
}
