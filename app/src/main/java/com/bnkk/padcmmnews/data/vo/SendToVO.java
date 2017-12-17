package com.bnkk.padcmmnews.data.vo;

import android.content.ContentValues;

import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class SendToVO {

    @SerializedName("send-to-id")
    private String sendToId;

    @SerializedName("sent-date")
    private String sendDate;

    @SerializedName("acted-user")
    private ActedUserVO sender;

    @SerializedName("received-user")
    private ActedUserVO receiver;

    public String getSendToId() {
        return sendToId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public ActedUserVO getSender() {
        return sender;
    }

    public ActedUserVO getReceiver() {
        return receiver;
    }

    public ContentValues parseToContent(String newsId) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(NewsContract.SendToEntry.COLUMN_SEND_TO_ID, sendToId);
        contentValues.put(NewsContract.SendToEntry.COLUMN_SEND_TO_DATE, sendDate);
        contentValues.put(NewsContract.SendToEntry.COLUMN_SENDER_ID, sender.getUserId());
        contentValues.put(NewsContract.SendToEntry.COLUMN_RECEIVER_ID, receiver.getUserId());
        contentValues.put(NewsContract.SendToEntry.COLUMN_NEWS_ID, newsId);

        return contentValues;
    }
}
