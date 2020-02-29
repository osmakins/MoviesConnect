package com.example.overcomerpc.moviesconnect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.overcomerpc.moviesconnect.adapters.MoviesAdapter;
import com.example.overcomerpc.moviesconnect.models.Movies;
import com.example.overcomerpc.moviesconnect.utils.JsonUtils;
import com.example.overcomerpc.moviesconnect.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;


public class MoviesListFragment extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MoviesAdapter mMoviesAdapter;
    private List<Movies> mMoviesList;
    @BindView(R.id.movies_recyclerview) RecyclerView movieRecyclerview;
    @BindView(R.id.pb_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessage;

    public MoviesListFragment(){
        // Mandatory empty constructor for the fragment class
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        movieRecyclerview = rootView.findViewById(R.id.movies_recyclerview);

        mMoviesList = new ArrayList<>();

        mMoviesAdapter = new MoviesAdapter(getContext(), mMoviesList);

        movieRecyclerview.setAdapter(mMoviesAdapter);
        movieRecyclerview.setLayoutManager(layoutManager);

        return rootView;
    }

    private void showJMoviesResults(){
        //make error message invisible, then make the json results visible.
        mErrorMessage.setVisibility(View.INVISIBLE);
        movieRecyclerview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        // Show json results and hide the error message.
        movieRecyclerview.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    // Fetch data from the internet on the background thread using AsyncTask
    public class MoviesConnectTask extends AsyncTask<String, Void, Movies[]> {

        // Note to self.
        // How to use a static inner AsyncTask class:
        // https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
        // Use volley or retrofit for stage 2.

        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movies[] doInBackground(String... params) {
            if(params.length == 0){
                return null;
            }

            String sortOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.buildUrl(sortOrder);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                Log.v(LOG_TAG, jsonMovieResponse.toString());
                return JsonUtils.getMoviesDataFromJson(jsonMovieResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movies[] moviesData) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(moviesData != null){
                mMoviesAdapter.clearRecyclerViewData();
                Collections.addAll(mMoviesList, moviesData);
                showJMoviesResults();
                mMoviesAdapter.notifyItemRangeInserted(0, moviesData.length);
            }else {
                showErrorMessage();
            }
        }
    }
}
