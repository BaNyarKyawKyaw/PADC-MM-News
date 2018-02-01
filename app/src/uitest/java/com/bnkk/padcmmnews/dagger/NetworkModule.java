package com.padcmyanmar.mmnews.dagger;

import com.padcmyanmar.mmnews.network.MMNewsDataAgent;
import com.padcmyanmar.mmnews.network.UITestDataAgentImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by E5-575G on 1/7/2018.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public MMNewsDataAgent provideMMNewsDataAgent() {
        return new UITestDataAgentImpl();
    }
}
