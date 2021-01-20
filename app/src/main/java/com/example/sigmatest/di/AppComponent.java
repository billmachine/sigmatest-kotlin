package com.example.sigmatest.di;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
//        UtilsModule.class,
//        AppModule.class,
//        ActivityBindingModule.class,
        RetrofitModule.class,
//        FirebaseModule.class,
        DataModule.class,
        UiModule.class,

        AndroidSupportInjectionModule.class
})

public interface AppComponent extends AndroidInjector<MyApplication> {

    // we can now do DaggerAppComponent.builder().application(this).build().inject(this),
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application2 will just be provided into our app graph

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(android.app.Application application);

        AppComponent build();
    }

}