package com.example.beetel.moviesapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.glidepalette.GlidePalette;
import com.example.beetel.moviesapp.R;
import com.example.beetel.moviesapp.data.model.MovieResultListModel.ResultModel;
import com.example.beetel.moviesapp.util.MovieApiUtility;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by beetel on 9/03/2016.
 */
public class RecyclerViewMoviesAdapter extends RecyclerView.Adapter<RecyclerViewMoviesAdapter.MoviesViewHolder> {

    private ArrayList<ResultModel> mresultListModel;
    private Context mContext;
    private MovieOnClickHandler movieOnClickHandler;


    public RecyclerViewMoviesAdapter(Context context, ArrayList<ResultModel> model, MovieOnClickHandler clickHandler) {
        this.mresultListModel = model;
        this.mContext = context;
        this.movieOnClickHandler = clickHandler;

    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        String url = MovieApiUtility.URL_IMAGE_BASE + mresultListModel.get(position).getPosterUrl();
        String title = mresultListModel.get(position).getTitle();
        holder.textViewTitle.setText(title);
        Glide.with(holder.imageView.getContext())
                .load(url)
                .listener(GlidePalette.with(url)
                                .use(GlidePalette.Profile.VIBRANT)
                                .intoBackground(holder.textViewTitle)
                                .crossfade(true)
                )
                .error(R.drawable.poster)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        if (mresultListModel != null)
            return mresultListModel.size();
        else
            return 0;
    }

    public interface MovieOnClickHandler {
        void onClick(MoviesViewHolder moviesViewHolder, ResultModel model, MoviesViewHolder holder);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.imageview_poster)
        ImageView imageView;
        @Bind(R.id.card_movietitle)
        TextView textViewTitle;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            ResultModel resultModel = mresultListModel.get(getAdapterPosition());
            movieOnClickHandler.onClick(this, resultModel, this);
        }
    }
}
