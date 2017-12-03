package com.bnkk.padcmmnews.data.vo;

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
}
