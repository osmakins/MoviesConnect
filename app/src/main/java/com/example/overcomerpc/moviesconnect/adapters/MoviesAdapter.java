package com.example.overcomerpc.moviesconnect.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.overcomerpc.moviesconnect.R;
import com.example.overcomerpc.moviesconnect.models.Movies;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private static final String LOG_TAG = MoviesAdapter.class.getSimpleName();

    private Context mContext;
    private List<Movies> mMoviesList;

    private MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler{
        void onItemClicked(int position);
    }

    public void setOnItemClicklistener(MoviesAdapterOnClickHandler handler){
        mClickHandler = handler;
    }

    public MoviesAdapter(Context context, List<Movies> moviesList){
        mMoviesList = moviesList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idLayoutListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(idLayoutListItem, viewGroup, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Movies movies = mMoviesList.get(position);

        holder.mMovieTitleTextView.setText(movies.getTitle());

        Glide.with(holder.mPosterImageView.getContext())
                .load(movies.getPosterUrl().toString())
                .into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        if(mMoviesList == null){
            Log.e(LOG_TAG,"List is empty!");
            return 0;
        }
        return mMoviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView mPosterImageView;
        public final TextView mMovieTitleTextView;

        public ViewHolder(View view){
            super(view);
            mPosterImageView = (ImageView) view.findViewById(R.id.movie_poster);
            mMovieTitleTextView = (TextView) view.findViewById(R.id.movie_title);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mClickHandler != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mClickHandler.onItemClicked(position);
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        Glide.with(holder.mPosterImageView.getContext())
                .clear(holder.mPosterImageView);
    }

    public void clearRecyclerViewData(){
        int size = mMoviesList.size();
        if(size > 0){
            for(int i = 0; i < size; i++){
                mMoviesList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}
