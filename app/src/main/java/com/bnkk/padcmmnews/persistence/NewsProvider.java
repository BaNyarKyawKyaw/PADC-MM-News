package com.bnkk.padcmmnews.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor queryCursor = mNewsDBHelper.getReadableDatabase().query(getTableName(uri),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        if (getContext() != null) {
            queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ACTED_USER:
                return NewsContract.ActedUserEntry.DIR_TYPE;
            case PUBLICATION:
                return NewsContract.PublicationsEntry.DIR_TYPE;
            case NEWS:
                return NewsContract.NewsEntry.DIR_TYPE;
            case IMAGES:
                return NewsContract.ImagesEntry.DIR_TYPE;
            case FAVOURITE_ACTION:
                return NewsContract.FavouriteActionsEntry.DIR_TYPE;
            case COMMENT_ACTION:
                return NewsContract.CommentActionsEntry.DIR_TYPE;
            case SEND_TO_ACTION:
                return NewsContract.SendToEntry.DIR_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long _id = db.insert(tableName, null, values);

        if (_id > 0) {
            Uri contentUri = getContentUri(uri);
            Uri insertedUri = ContentUris.withAppendedId(contentUri, _id);

            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return insertedUri;
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);

        if (getContext() != null && rowDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, values, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_ACTED_USER, ACTED_USER);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_PUBLICATION, PUBLICATION);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS, NEWS);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_IMAGES, IMAGES);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_FAVOURITE_ACTION, FAVOURITE_ACTION);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_COMMENTS, COMMENT_ACTION);
        uriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_SEND_TO, SEND_TO_ACTION);

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
        }
        return null;
    }

    private Uri getContentUri(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case ACTED_USER:
                return NewsContract.ActedUserEntry.CONTENT_URI;
            case PUBLICATION:
                return NewsContract.PublicationsEntry.CONTENT_URI;
            case NEWS:
                return NewsContract.NewsEntry.CONTENT_URI;
            case IMAGES:
                return NewsContract.ImagesEntry.CONTENT_URI;
            case FAVOURITE_ACTION:
                return NewsContract.FavouriteActionsEntry.CONTENT_URI;
            case COMMENT_ACTION:
                return NewsContract.CommentActionsEntry.CONTENT_URI;
            case SEND_TO_ACTION:
                return NewsContract.SendToEntry.CONTENT_URI;
        }
        return null;
    }
}
