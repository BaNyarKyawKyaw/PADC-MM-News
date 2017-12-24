package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    @SerializedName("sent-tos")
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
        if (images == null)
            images = new ArrayList<>();

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

    public static NewsVO parseFromCursor(Context context, Cursor cursor) {

        NewsVO news = new NewsVO();
        news.newsId = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_ID));
        news.brief = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_BRIEF));
        news.details = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DETAILS));
        news.postedDate = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_POSTED_DATE));

        news.publication = PublicationVO.parseFromCursor(cursor);
        news.images = loadImagesInNews(context, news.newsId);
        news.favouriteActions = loadFavouriteActionsInNews(context, news.newsId);
        news.comments = loadCommentActionInNews(context, news.newsId);
        news.sendTos = loadSendToInNews(context, news.newsId);

        return news;
    }

    private static List<String> loadImagesInNews(Context context, String newsId) {
        Cursor imagesInNewsCursor = context.getContentResolver().query(NewsContract.ImagesEntry.CONTENT_URI,
                null,
                NewsContract.ImagesEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null
        );

        if (imagesInNewsCursor != null && imagesInNewsCursor.moveToFirst()) {
            List<String> imagesInNews = new ArrayList<>();
            do {
                imagesInNews.add(
                        imagesInNewsCursor.getString(
                                imagesInNewsCursor.getColumnIndex(NewsContract.ImagesEntry.COLUMN_NEWS_ID)
                        ));
            } while (imagesInNewsCursor.moveToNext());
            imagesInNewsCursor.close();
            return imagesInNews;
        }

        return null;
    }

    public static List<FavouriteActionVO> loadFavouriteActionsInNews(Context context, String newsId) {
        Cursor favouriteInNewsCursor = context.getContentResolver().query(NewsContract.FavouriteActionsEntry.CONTENT_URI,
                null,
                NewsContract.FavouriteActionsEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null
        );

        if (favouriteInNewsCursor != null && favouriteInNewsCursor.moveToFirst()) {
            List<FavouriteActionVO> favoriteInNews = new ArrayList<>();
            do {
                favoriteInNews.add(
                        FavouriteActionVO.parseFromCursor(favouriteInNewsCursor)
                );
            } while (favouriteInNewsCursor.moveToNext());
            favouriteInNewsCursor.close();
            return favoriteInNews;
        }

        return null;
    }

    public static List<CommentsVO> loadCommentActionInNews(Context context, String newsId) {
        Cursor commentInNewsCursor = context.getContentResolver().query(NewsContract.CommentActionsEntry.CONTENT_URI,
                null,
                NewsContract.CommentActionsEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null
        );

        if (commentInNewsCursor != null && commentInNewsCursor.moveToFirst()) {
            List<CommentsVO> commentsInNews = new ArrayList<>();
            do {
                commentsInNews.add(CommentsVO.parseFromCursor(commentInNewsCursor));
            } while (commentInNewsCursor.moveToNext());
            commentInNewsCursor.close();
            return commentsInNews;
        }

        return null;
    }

    public static List<SendToVO> loadSendToInNews(Context context, String newsId) {
        Cursor sendToInNewCursor = context.getContentResolver().query(NewsContract.SendToEntry.CONTENT_URI,
                null,
                NewsContract.SendToEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null
        );

        if (sendToInNewCursor != null && sendToInNewCursor.moveToFirst()) {
            List<SendToVO> sendToInNews = new ArrayList<>();
            do {
                sendToInNews.add(SendToVO.parseFromCursor(sendToInNewCursor));
            } while (sendToInNewCursor.moveToNext());
            sendToInNewCursor.close();
            return sendToInNews;
        }

        return null;
    }
}
