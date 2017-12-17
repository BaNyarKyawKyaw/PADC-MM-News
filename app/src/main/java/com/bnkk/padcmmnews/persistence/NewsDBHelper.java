package com.bnkk.padcmmnews.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bnkk.padcmmnews.persistence.NewsContract.NewsEntry;
import com.bnkk.padcmmnews.persistence.NewsContract.ImagesEntry;
import com.bnkk.padcmmnews.persistence.NewsContract.PublicationsEntry;
import com.bnkk.padcmmnews.persistence.NewsContract.FavouriteActionsEntry;
import com.bnkk.padcmmnews.persistence.NewsContract.CommentActionsEntry;
import com.bnkk.padcmmnews.persistence.NewsContract.SendToEntry;
import com.bnkk.padcmmnews.persistence.NewsContract.ActedUserEntry;


/**
 * Created by E5-575G on 12/11/2017.
 */

public class NewsDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news.db";

    private static final String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + NewsEntry.TABLE_NAME + " (" +
            NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NewsEntry.COLUMN_NEWS_ID + "VARCHAR(5), " +
            NewsEntry.COLUMN_BRIEF + "TEXT, " +
            NewsEntry.COLUMN_DETAILS + "TEXT, " +
            NewsEntry.COLUMN_POSTED_DATE + "VARCHAR(10), " +
            NewsEntry.COLUMN_PUBLICATION_ID + "VARCHAR(5), " +

            " UNIQUE (" + NewsEntry.COLUMN_NEWS_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_IMAGES_TABLE = "CREATE TABLE " + ImagesEntry.TABLE_NAME + " (" +
            ImagesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ImagesEntry.COLUMN_IMAGE_URL + "TEXT, " +
            ImagesEntry.COLUMN_NEWS_ID + "VARCHAR(5), " +

            " UNIQUE (" + ImagesEntry.COLUMN_IMAGE_URL + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_PUBLICATION_TABLE = "CREATE TABLE " + PublicationsEntry.TABLE_NAME + " (" +
            PublicationsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PublicationsEntry.COLUMN_PUBLICATION_ID + "VARCHAR(5), " +
            PublicationsEntry.COLUMN_TITLE + "TEXT, " +
            PublicationsEntry.COLUMN_LOGO + "TEXT, " +

            " UNIQUE (" + PublicationsEntry.COLUMN_PUBLICATION_ID + ") ON CONFLICT REPLACE, " +
            " UNIQUE (" + PublicationsEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_FAVOURITE_ACTIONS_TABLE = "CREATE TABLE " + FavouriteActionsEntry.TABLE_NAME + " (" +
            FavouriteActionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FavouriteActionsEntry.COLUMN_FAVOURITE_ID + "VARCHAR(5), " +
            FavouriteActionsEntry.COLUMN_FAVOURITE_DATE + "VARCHAR(10), " +
            FavouriteActionsEntry.COLUMN_NEWS_ID + "VARCHAR(5), " +
            FavouriteActionsEntry.COLUMN_USER_ID + "VARCHAR(5), " +

            " UNIQUE (" + FavouriteActionsEntry.COLUMN_FAVOURITE_ID + ") ON CONFLICT REPLACE, " +
            " );";

    private static final String SQL_CREATE_COMMENT_ACTIONS_TABLE = "CREATE TABLE " + CommentActionsEntry.TABLE_NAME + " (" +
            CommentActionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CommentActionsEntry.COLUMN_COMMENT_ID + "VARCHAR(5), " +
            CommentActionsEntry.COLUMN_COMMENT + "TEXT, " +
            CommentActionsEntry.COLUMN_COMMENT_DATE + "VARCHAR(10), " +
            CommentActionsEntry.COLUMN_USER_ID + "VARCHAR(5), " +
            CommentActionsEntry.COLUMN_NEWS_ID + "VARCHAR(5), " +

            " UNIQUE (" + CommentActionsEntry.COLUMN_COMMENT_ID + ") ON CONFLICT REPLACE, " +
            " );";

    private static final String SQL_CREATE_SEND_TO_TABLE = "CREATE TABLE " + SendToEntry.TABLE_NAME + " (" +
            SendToEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SendToEntry.COLUMN_SEND_TO_ID + "VARCHAR(5), " +
            SendToEntry.COLUMN_SEND_TO_DATE + "VARCHAR(10), " +
            SendToEntry.COLUMN_SENDER_ID + "VARCHAR(5), " +
            SendToEntry.COLUMN_RECEIVER_ID + "VARCHAR(5), " +
            SendToEntry.COLUMN_NEWS_ID + "VARCHAR(5), " +

            " UNIQUE (" + SendToEntry.COLUMN_SEND_TO_ID + ") ON CONFLICT REPLACE, " +
            " );";

    private static final String SQL_CREATE_ACTED_USER_TABLE = "CREATE TABLE " + ActedUserEntry.TABLE_NAME + " (" +
            ActedUserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ActedUserEntry.COLUMN_USER_ID + "VARCHAR(5), " +
            ActedUserEntry.TABLE_NAME + "VARCHAR(50), " +
            ActedUserEntry.COLUMN_PROFILE_IMAGE + "TEXT, " +

            " UNIQUE (" + ActedUserEntry.COLUMN_USER_ID + ") ON CONFLICT REPLACE, " +
            " );";

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PUBLICATION_TABLE);
        db.execSQL(SQL_CREATE_ACTED_USER_TABLE);
        db.execSQL(SQL_CREATE_NEWS_TABLE);
        db.execSQL(SQL_CREATE_IMAGES_TABLE);

        db.execSQL(SQL_CREATE_FAVOURITE_ACTIONS_TABLE);
        db.execSQL(SQL_CREATE_COMMENT_ACTIONS_TABLE);
        db.execSQL(SQL_CREATE_SEND_TO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + SendToEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CommentActionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteActionsEntry.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + ImagesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ActedUserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PublicationsEntry.TABLE_NAME);

        onCreate(db);
    }
}
