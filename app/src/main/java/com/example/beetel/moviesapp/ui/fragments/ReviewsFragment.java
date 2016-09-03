package com.example.beetel.moviesapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beetel.moviesapp.R;
import com.example.beetel.moviesapp.data.api.MovieApi;
import com.example.beetel.moviesapp.data.api.RServiceGenerator;
import com.example.beetel.moviesapp.data.model.MovieResultListModel;
import com.example.beetel.moviesapp.data.model.MovieReviewListModel;
import com.example.beetel.moviesapp.ui.adapter.RecyclerViewReviewAdapter;
import com.example.beetel.moviesapp.util.MovieApiUtility;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by beetel on 16/03/2016.
 */
public class ReviewsFragment extends Fragment {

    private static final String LOG_TAG = ReviewsFragment.class.getSimpleName();
    private MovieApi movieApi;
    private Subscription reviewSubscription;
    private ArrayList<MovieReviewListModel.ReviewModel> reviewList;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewReviewAdapter mReviewAdapter;

    @Bind(R.id.recycler_view_movie_review)
    RecyclerView mRecyclerView;

    public ReviewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        ButterKnife.bind(this, view);


        Bundle bundle = getArguments();
        MovieResultListModel.ResultModel resultModel = bundle.getParcelable(MovieApiUtility.EXTRA_REVIEW_FRAGMENT);

        String id = null;
        if (resultModel != null) {
            id = resultModel.getMovieId();
        }

        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        if (savedInstanceState != null && savedInstanceState.containsKey(MovieApiUtility.EXTRA_REVIEW)) {
            reviewList = savedInstanceState.getParcelableArrayList(MovieApiUtility.EXTRA_REVIEW);
            mReviewAdapter = new RecyclerViewReviewAdapter(reviewList, getActivity());
            mReviewAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mReviewAdapter);
        }

        callReview(id);
        return view;
    }


    private void callReview(String id) {
        movieApi = RServiceGenerator.createService(MovieApi.class);
        reviewSubscription = movieApi.getMovieReviewRx(id, getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MovieReviewListModel>() {
                    @Override
                    public void onCompleted() {
                        mReviewAdapter = new RecyclerViewReviewAdapter(reviewList, getActivity());
                        mReviewAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mReviewAdapter);
                        Log.d(LOG_TAG, "Completed loading movie videos");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieReviewListModel movieReviewListModel) {
                        reviewList = new ArrayList<>();
                        for (int i = 0; i < movieReviewListModel.getResults().size(); i++) {
                            reviewList.add(movieReviewListModel.getResults().get(i));
                            Log.d(LOG_TAG, movieReviewListModel.getResults().get(i).getContent());
                        }
                    }
                });

    }

    @Override
    public void onPause() {
        super.onPause();
        reviewSubscription.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MovieApiUtility.EXTRA_REVIEW, reviewList);
    }
}
