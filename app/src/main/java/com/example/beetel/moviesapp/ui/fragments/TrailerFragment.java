package com.example.beetel.moviesapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.beetel.moviesapp.R;
import com.example.beetel.moviesapp.data.api.MovieApi;
import com.example.beetel.moviesapp.data.api.RServiceGenerator;
import com.example.beetel.moviesapp.data.model.MovieResultListModel;
import com.example.beetel.moviesapp.data.model.MovieVideoListModel;
import com.example.beetel.moviesapp.ui.adapter.RecyclerViewVideoAdapter;
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
public class TrailerFragment extends Fragment {

    private static final String LOG_TAG = TrailerFragment.class.getSimpleName();
    private MovieApi movieApi;
    private Subscription videoSubscription;
    private ArrayList<MovieVideoListModel.VideoModel> mVideoList = null;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewVideoAdapter videoAdapter;
    private MovieResultListModel.ResultModel resultModel;
    private ShareActionProvider shareActionProvider = null;


    @Bind(R.id.recycler_view_movie_trailer)
    RecyclerView mRecyclerView;


    public TrailerFragment() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_trailer, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        MovieResultListModel.ResultModel resultModel = bundle.getParcelable(MovieApiUtility.EXTRA_TRAILER_FRAGMENT);
        getActivity().supportInvalidateOptionsMenu();
        String id = null;
        if (resultModel != null) {
            id = resultModel.getMovieId();
        }
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        if (savedInstanceState != null && savedInstanceState.containsKey(MovieApiUtility.EXTRA_VIDEO)) {
            mVideoList = savedInstanceState.getParcelableArrayList(MovieApiUtility.EXTRA_VIDEO);
            videoAdapter = new RecyclerViewVideoAdapter(mVideoList, getActivity());
            videoAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(videoAdapter);
        }

        callTrailer(id);
        return view;
    }

    private void callTrailer(String id) {
        movieApi = RServiceGenerator.createService(MovieApi.class);
        videoSubscription = movieApi.getMovieTrailerRx(id, getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MovieVideoListModel>() {
                    @Override
                    public void onCompleted() {
                        videoAdapter = new RecyclerViewVideoAdapter(mVideoList, getActivity());
                        videoAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(videoAdapter);
                        Log.d(LOG_TAG, "Completed loading movie videos");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieVideoListModel movieVideoListModel) {
                        mVideoList = new ArrayList<>();
                        for (int i = 0; i < movieVideoListModel.getResults().size(); i++) {
                            mVideoList.add(movieVideoListModel.getResults().get(i));
                            Log.d(LOG_TAG, MovieApiUtility.YOUTUBE_THUMBNAIL_URL_BASE + movieVideoListModel.getResults().get(i).getKey() + "/default.jpg");
                        }
                    }
                });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_trailer_fragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share_trailer);
        String url = "No video found";

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        try {
            url = MovieApiUtility.YOUTUBE_PLAYER_URL_BASE + mVideoList.get(0).getKey();
        } catch (IndexOutOfBoundsException | NullPointerException e) {

        }

        intent.putExtra(Intent.EXTRA_TEXT, url);
        shareActionProvider.setShareIntent(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MovieApiUtility.EXTRA_VIDEO, mVideoList);
    }


}
