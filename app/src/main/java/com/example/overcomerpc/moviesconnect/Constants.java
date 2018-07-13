package com.example.overcomerpc.moviesconnect;

public class Constants {
    // Connection constants
    public final static String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public final static String POSTER_WIDTH = "w185";
    public final static String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
    public final static String TOP_RATED_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";
    public final static String API_KEY = "api_key";

    // You should obtain an api key from themoviedb.com and fix it in the API_KEY_PARAM value.
    public final static String API_KEY_PARAM = "";

    // Sorting constants
    public static final String SORT_POPULAR = "popular";
    public static final String SORT_TOPRATED = "top_rated";

    // Declare json constants
    public static final String JSON_LIST = "results";
    public static final String MESSAGE_CODE = "cod";

    // String constants
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String RELEASE_DATE = "release_date";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "poster_path";
}
