package com.example.overcomerpc.moviesconnect.utils;

import android.net.Uri;
import android.util.Log;

import com.example.overcomerpc.moviesconnect.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    // Declare network connection constants.
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Build the json connect url
    public static URL buildUrl(String sortBy){
        Uri builtUri = null;
        switch(sortBy){
            case Constants.SORT_POPULAR:
                builtUri = Uri.parse(Constants.POPULAR_MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(Constants.API_KEY, Constants.API_KEY_PARAM)
                        .build();
                break;
            case Constants.SORT_TOPRATED:
                builtUri = Uri.parse(Constants.TOP_RATED_MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(Constants.API_KEY, Constants.API_KEY_PARAM)
                        .build();
                break;
            default:
                builtUri = Uri.parse(Constants.POPULAR_MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(Constants.API_KEY, Constants.API_KEY_PARAM)
                        .build();
                break;
        }

        URL url = null;

        try {
            url = new URL(builtUri.toString());
            Log.v(LOG_TAG, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Build image url
    public static URL posterUrl(String posterPath){
        Uri builtUri = null;
        builtUri = Uri.parse(Constants.MOVIE_POSTER_BASE_URL).buildUpon()
                .appendEncodedPath(Constants.POSTER_WIDTH)
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
            Log.v(LOG_TAG, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){return scanner.next();}
            else{return null;}
        } finally {
            urlConnection.disconnect();
        }
    }
}
