package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;

import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class FavouriteActionVO {

    @SerializedName("favourite-id")
    private String favouriteId;

    @SerializedName("favourite-date")
    private String favouriteDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    public String getFavouriteId() {
        return favouriteId;
    }

    public String getFavouriteDate() {
        return favouriteDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public ContentValues parseToContentValues(String newsId) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(NewsContract.FavouriteActionsEntry.COLUMN_FAVOURITE_ID, favouriteId);
        contentValues.put(NewsContract.FavouriteActionsEntry.COLUMN_FAVOURITE_DATE, favouriteDate);
        contentValues.put(NewsContract.FavouriteActionsEntry.COLUMN_USER_ID, actedUser.getUserId());
        contentValues.put(NewsContract.FavouriteActionsEntry.COLUMN_NEWS_ID, newsId);

        return contentValues;
    }
}
