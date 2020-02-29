package com.example.overcomerpc.moviesconnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.overcomerpc.moviesconnect.adapters.MoviesAdapter;
import com.example.overcomerpc.moviesconnect.models.Movies;
import com.example.overcomerpc.moviesconnect.utils.JsonUtils;
import com.example.overcomerpc.moviesconnect.utils.NetworkUtils;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MoviesAdapter mMoviesAdapter;
    private List<Movies> mMoviesList;

    // The main activity views
    @BindView(R.id.movies_recyclerview) RecyclerView movieRecyclerview;
    @BindView(R.id.pb_loading) ProgressBar mProgressBar;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        movieRecyclerview.setLayoutManager(layoutManager);
        movieRecyclerview.setHasFixedSize(true);
        mMoviesList = new ArrayList<>();

        mMoviesAdapter = new MoviesAdapter(getApplicationContext(), mMoviesList);
        movieRecyclerview.setAdapter(mMoviesAdapter);
        mMoviesAdapter.setOnItemClicklistener(MainActivity.this);

        new MoviesConnectTask(this).execute(ClickItem(0));
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

    // Method for handling the setting's sort choice
    private String ClickItem(int item){
        String sorting;
        switch (item){
            case R.id.sort_popular:
                sorting = Constants.SORT_POPULAR;
                break;
            case R.id.sort_toprated:
                sorting = Constants.SORT_TOPRATED;
                break;
            default:
                sorting = Constants.SORT_POPULAR;
                break;
        }
        return sorting;
    }

    @Override
    public void onItemClicked(int position) {
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        Movies clickItem = mMoviesList.get(position);

        // Code review suggestion - putParcelableExtra and getParcelableExtra is more robust.
        // https://www.sitepoint.com/transfer-data-between-activities-with-android-parcelable/

        detailsIntent.putExtra(Constants.POSTER_PATH, clickItem.getPosterUrl().toString());
        detailsIntent.putExtra(Constants.TITLE, clickItem.getTitle());
        detailsIntent.putExtra(Constants.RELEASE_DATE, clickItem.getReleaseDate());
        detailsIntent.putExtra(Constants.VOTE_AVERAGE, clickItem.getVoteAverage());
        detailsIntent.putExtra(Constants.OVERVIEW, clickItem.getOverview());

        startActivity(detailsIntent);
    }

    // Fetch data from the internet on the background thread using AsyncTask
    public class MoviesConnectTask extends AsyncTask<String, Void, Movies[]> {

        // Note to self.
        // How to use a static inner AsyncTask class:
        // https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
        // Use volley or retrofit for stage 2.

        private final WeakReference<MainActivity> activityReference;

        MoviesConnectTask(MainActivity context){
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            activity.mProgressBar.setVisibility(View.VISIBLE);
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
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            activity.mProgressBar.setVisibility(View.INVISIBLE);
            if(moviesData != null){
                activity.mMoviesAdapter.clearRecyclerViewData();
                Collections.addAll(activity.mMoviesList, moviesData);
                activity.showJMoviesResults();
                activity.mMoviesAdapter.notifyItemRangeInserted(0,moviesData.length);
            }else {
                activity.showErrorMessage();
            }
        }
    }
    // Settings menu options and item selection
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        String sortOrder = ClickItem(itemThatWasClickedId);
        new MoviesConnectTask(this).execute(sortOrder);
        return super.onOptionsItemSelected(item);
    }
}

