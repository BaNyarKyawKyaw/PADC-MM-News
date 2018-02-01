package com.bnkk.padcmmnews.dagger;

import com.bnkk.padcmmnews.network.MMNewDataAgent;
import com.bnkk.padcmmnews.network.MMNewsDataAgentImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by E5-575G on 2/1/2018.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public MMNewDataAgent provideMMNewsDataAgent() {
        return new MMNewsDataAgentImpl();
    }
}
