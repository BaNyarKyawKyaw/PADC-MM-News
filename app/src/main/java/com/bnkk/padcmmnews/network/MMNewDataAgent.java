package com.bnkk.padcmmnews.network;

import android.content.Context;

/**
 * Created by E5-575G on 12/3/2017.
 */

public interface MMNewDataAgent {

    void loadMMNews(String accessToken, int pageNo, Context context);
}
