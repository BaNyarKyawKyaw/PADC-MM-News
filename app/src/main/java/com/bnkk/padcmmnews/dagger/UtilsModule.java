package com.bnkk.padcmmnews.dagger;

import android.content.Context;

import com.bnkk.padcmmnews.utils.ConfigUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by E5-575G on 2/1/2018.
 */

@Module
public class UtilsModule {

    @Provides
    @Singleton
    public ConfigUtils provideConfigUtils(Context context) {
        return new ConfigUtils(context);
    }
}
