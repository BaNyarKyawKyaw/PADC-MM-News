package com.bnkk.padcmmnews.network;

import com.bnkk.padcmmnews.network.responses.GetNewsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by E5-575G on 12/3/2017.
 */

public interface MMNewsAPI {

    @FormUrlEncoded
    @POST("v1/getMMNews.php")
    Call<GetNewsResponse> loadMMNews(
            @Field("page") int pageIndex,
            @Field("access_token") String accessToken);
}
