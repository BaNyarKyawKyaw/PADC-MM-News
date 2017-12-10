package com.bnkk.padcmmnews.network;

import com.bnkk.padcmmnews.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by E5-575G on 12/9/2017.
 */

public abstract class SFCCallBack<T extends SFCResponse> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        SFCResponse SFCResponse = response.body();
        if (SFCResponse == null) {
            RestApiEvents.ErrorInvokingAPIEvent errorEvent
                    = new RestApiEvents.ErrorInvokingAPIEvent("No data could be loaded for now. Pls try again later");
            EventBus.getDefault().post(errorEvent);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        RestApiEvents.ErrorInvokingAPIEvent errorEvent
                = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
        EventBus.getDefault().post(errorEvent);
    }
}
