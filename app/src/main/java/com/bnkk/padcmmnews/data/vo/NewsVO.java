package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by E5-575G on 12/2/2017.
 */

public class NewsVO {

    @SerializedName("news-id")
    private String newsId;

    @SerializedName("brief")
    private String brief;

    @SerializedName("details")
    private String details;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("posted-date")
    private String postedDate;

    @SerializedName("publication")
    private PublicationVO publication;

    @SerializedName("favorites")
    private List<FavouriteActionVO> favouriteActions;

    @SerializedName("comments")
    private List<CommentsVO> comments;

    @SerializedName("send-tos")
    private List<SendToVO> sendTos;

    public String getNewsId() {
        return newsId;
    }

    public String getBrief() {
        return brief;
    }

    public String getDetails() {
        return details;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public PublicationVO getPublication() {
        return publication;
    }

    public List<FavouriteActionVO> getFavouriteActions() {
        return favouriteActions;
    }

    public List<CommentsVO> getComments() {
        return comments;
    }

    public List<SendToVO> getSendTos() {
        return sendTos;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(NewsContract.NewsEntry.COLUMN_NEWS_ID, newsId);
        contentValues.put(NewsContract.NewsEntry.COLUMN_BRIEF, brief);
        contentValues.put(NewsContract.NewsEntry.COLUMN_DETAILS, details);
        contentValues.put(NewsContract.NewsEntry.COLUMN_POSTED_DATE, postedDate);
        contentValues.put(NewsContract.NewsEntry.COLUMN_PUBLICATION_ID, publication.getPublicationId());

        return contentValues;
    }

    public static NewsVO parseFromCursor(Cursor cursor) {

        NewsVO news = new NewsVO();
        news.newsId = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_ID));
        news.brief = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_BRIEF));
        news.details = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DETAILS));
        news.postedDate = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_POSTED_DATE));

        return news;
    }
}
