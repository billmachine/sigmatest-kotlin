package com.example.sigmatest.di;

import android.content.Context;

import androidx.multidex.MultiDex;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApplication extends DaggerApplication {

    public static AppComponent myComponent;
    {
        myComponent = myComponent();
    }

    public AppComponent appComponent = myComponent();
    public AppComponent myComponent(){
        return  DaggerAppComponent
                .builder()
                .application(this)
                .build();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
