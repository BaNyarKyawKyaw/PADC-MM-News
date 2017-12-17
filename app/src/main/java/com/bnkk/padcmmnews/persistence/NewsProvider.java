package com.bnkk.padcmmnews.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by E5-575G on 12/16/2017.
 */

public class NewsProvider extends ContentProvider {

    public static final int ACTED_USER = 100;
    public static final int PUBLICATION = 200;
    public static final int NEWS = 300;
    public static final int IMAGES = 400;
    public static final int FAVOURITE_ACTION = 500;
    public static final int COMMENT_ACTION = 600;
    public static final int SEND_TO_ACTION = 700;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private NewsDBHelper mNewsDBHelper;

    @Override
    public boolean onCreate() {
        mNewsDBHelper = new NewsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_ACTED_USER, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_PUBLICATION, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_IMAGES, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_FAVOURITE_ACTION, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_COMMENTS, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_SEND_TO, ACTED_USER);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ACTED_USER:
                return NewsContract.ActedUserEntry.TABLE_NAME;
            case PUBLICATION:
                return NewsContract.PublicationsEntry.TABLE_NAME;
            case NEWS:
                return NewsContract.NewsEntry.TABLE_NAME;
            case IMAGES:
                return NewsContract.ImagesEntry.TABLE_NAME;
            case FAVOURITE_ACTION:
                return NewsContract.FavouriteActionsEntry.TABLE_NAME;
            case COMMENT_ACTION:
                return NewsContract.CommentActionsEntry.TABLE_NAME;
            case SEND_TO_ACTION:
                return NewsContract.SendToEntry.TABLE_NAME;
            default:
                return null;
        }
    }
}
