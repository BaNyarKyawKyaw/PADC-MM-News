package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class CommentsVO {

    @SerializedName("comment-id")
    private String commentId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("comment-date")
    private String commentDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    public String getCommentId() {
        return commentId;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public ContentValues parseToContentValues(String newsId) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(NewsContract.CommentActionsEntry.COLUMN_COMMENT_ID, commentId);
        contentValues.put(NewsContract.CommentActionsEntry.COLUMN_COMMENT, comment);
        contentValues.put(NewsContract.CommentActionsEntry.COLUMN_COMMENT_DATE, commentDate);
        contentValues.put(NewsContract.CommentActionsEntry.COLUMN_USER_ID, actedUser.getUserId());
        contentValues.put(NewsContract.CommentActionsEntry.COLUMN_NEWS_ID, newsId);

        return contentValues;
    }

    public static CommentsVO parseFromCursor(Cursor cursor) {

        CommentsVO comments = new CommentsVO();

        comments.commentId = cursor.getString(cursor.getColumnIndex(NewsContract.CommentActionsEntry.COLUMN_COMMENT_ID));
        comments.comment = cursor.getString(cursor.getColumnIndex(NewsContract.CommentActionsEntry.COLUMN_COMMENT));
        comments.commentDate = cursor.getString(cursor.getColumnIndex(NewsContract.CommentActionsEntry.COLUMN_COMMENT_DATE));

        comments.actedUser = ActedUserVO.parseFromCursor(cursor);

        return comments;
    }
}
