package com.bnkk.padcmmnews.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.bnkk.padcmmnews.MMNewsApp;

/**
 * Created by E5-575G on 12/10/2017.
 */

public class NewsContract {

    public static final String CONTENT_AUTHORITY = MMNewsApp.class.getPackage().getName();
    //com.bnkk.padcmmnews

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //content://com.bnkk.padcmmnews

    public static final String PATH_NEWS = "news";
    public static final String PATH_IMAGES = "images";
    public static final String PATH_PUBLICATION = "publication";
    public static final String PATH_FAVOURITE_ACTION = "favourite_actions";
    public static final String PATH_COMMENTS = "comment_actions";
    public static final String PATH_SEND_TO = "send_to";
    public static final String PATH_ACTED_USER = "acted_user";

    public static final class NewsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();
        //content://com.bnkk.padcmmnews/news

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/news

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/news

        public static final String TABLE_NAME = PATH_NEWS;

        public static final String COLUMN_NEWS_ID = "news_id";
        public static final String COLUMN_BRIEF = "brief";
        public static final String COLUMN_DETAILS = "details";
        public static final String COLUMN_POSTED_DATE = "posted_date";
        public static final String COLUMN_PUBLICATION_ID = "publication_id";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/news/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ImagesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMAGES).build();
        //content://com.bnkk.padcmmnews/images

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMAGES;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/images

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMAGES;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/images

        public static final String TABLE_NAME = PATH_IMAGES;

        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_NEWS_ID = "news_id";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PublicationsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PUBLICATION).build();
        //content://com.bnkk.padcmmnews/publication

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PUBLICATION;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/publication

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PUBLICATION;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/publication

        public static final String TABLE_NAME = PATH_PUBLICATION;

        public static final String COLUMN_PUBLICATION_ID = "publication_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LOGO = "logo";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/publication/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FavouriteActionsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_ACTION).build();
        //content://com.bnkk.padcmmnews/favourite_actions

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE_ACTION;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/favourite_actions

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE_ACTION;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/favourite_actions

        public static final String TABLE_NAME = PATH_FAVOURITE_ACTION;

        public static final String COLUMN_FAVOURITE_ID = "favourite_id";
        public static final String COLUMN_FAVOURITE_DATE = "favourite_date";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NEWS_ID = "news_id";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/favourite_actions/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CommentActionsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMENTS).build();
        //content://com.bnkk.padcmmnews/comment_actions

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMENTS;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/comment_actions

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMENTS;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/comment_actions

        public static final String TABLE_NAME = PATH_COMMENTS;

        public static final String COLUMN_COMMENT_ID = "comment_id";
        public static final String COLUMN_COMMENT = "comment";
        public static final String COLUMN_COMMENT_DATE = "comment_date";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NEWS_ID = "news_id";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/comment_actions/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SendToEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEND_TO).build();
        //content://com.bnkk.padcmmnews/send_to

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEND_TO;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/send_to

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEND_TO;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/send_to

        public static final String TABLE_NAME = PATH_SEND_TO;

        public static final String COLUMN_SEND_TO_ID = "send_to_id";
        public static final String COLUMN_SEND_TO_DATE = "sent_date";
        public static final String COLUMN_SENDER_ID = "sender_id";
        public static final String COLUMN_RECEIVER_ID = "receiver_user_id";
        public static final String COLUMN_NEWS_ID = "news_id";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/send_to/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ActedUserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTED_USER).build();
        //content://com.bnkk.padcmmnews/user

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTED_USER;
        //vnd.android.cursor.dir/com.bnkk.padcmmnews/user

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTED_USER;
        //vnd.android.cursor.item/*/com.bnkk.padcmmnews/user

        public static final String TABLE_NAME = PATH_ACTED_USER;

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_PROFILE_IMAGE = "profile_image";

        public static Uri buildContentUri(long id) {
            //content://com.bnkk.padcmmnews/user/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
