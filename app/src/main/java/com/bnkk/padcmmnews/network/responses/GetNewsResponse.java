package com.bnkk.padcmmnews.network.responses;

import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.network.SFCResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class GetNewsResponse extends SFCResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("page")
    private int pageNo;

    @SerializedName("mmNews")
    private List<NewsVO> newsList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getPageNo() {
        return pageNo;
    }

    public List<NewsVO> getNewsList() {
        if (newsList == null) {
            newsList = new ArrayList<>();
        }
        return newsList;
    }
}
