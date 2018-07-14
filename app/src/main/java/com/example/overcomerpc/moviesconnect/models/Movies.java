package com.example.overcomerpc.moviesconnect.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class Movies implements Parcelable {
    private String mId;
    private String mTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mOverview;
    private URL mPosterUrl;

    public Movies(String id, String title, String releaseDate, String voteAverage, String overview, URL posterUrl){
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mOverview = overview;
        mPosterUrl = posterUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String Id) {
        mId = Id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        mTitle = Title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String ReleaseDate) {
        mReleaseDate = ReleaseDate;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String VoteAverage) {
        mVoteAverage = VoteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String Overview) {
        mOverview = Overview;
    }

    public URL getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(URL PosterUrl) {
        mPosterUrl = PosterUrl;
    }

    public Movies(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readString();
        mOverview = in.readString();
        mPosterUrl = (URL) in.readValue(Movies.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {

        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mVoteAverage);
        dest.writeString(mOverview);
        dest.writeValue(mPosterUrl);
    }
}
