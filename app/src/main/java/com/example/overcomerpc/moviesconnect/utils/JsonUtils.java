package com.example.overcomerpc.moviesconnect.utils;

import com.example.overcomerpc.moviesconnect.Constants;
import com.example.overcomerpc.moviesconnect.models.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtils {
    public static Movies[] getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        // Check valid json results
        if(moviesJson.has(Constants.MESSAGE_CODE)){
            int errorCode = moviesJson.getInt(Constants.MESSAGE_CODE);
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        // Loop through the json results.
        JSONArray jsonMoviesArray = moviesJson.getJSONArray(Constants.JSON_LIST);
        Movies[] moviesArray = new Movies[jsonMoviesArray.length()];

        for (int i = 0; i < jsonMoviesArray.length(); i++) {
            String id = jsonMoviesArray.getJSONObject(i).optString(Constants.ID);
            String title = jsonMoviesArray.getJSONObject(i).optString(Constants.TITLE);
            String releaseDate = jsonMoviesArray.getJSONObject(i).optString(Constants.RELEASE_DATE);
            String voteAverage = jsonMoviesArray.getJSONObject(i).optString(Constants.VOTE_AVERAGE);
            String overview = jsonMoviesArray.getJSONObject(i).optString(Constants.OVERVIEW);
            URL posterUrl = NetworkUtils.posterUrl(jsonMoviesArray.getJSONObject(i).optString(Constants.POSTER_PATH));

            moviesArray[i] = new Movies(id, title, releaseDate, voteAverage, overview,
                    posterUrl);
        }
        return moviesArray;
    }
}
