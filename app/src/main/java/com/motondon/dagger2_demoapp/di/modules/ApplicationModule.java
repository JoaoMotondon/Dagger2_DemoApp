package com.motondon.dagger2_demoapp.di.modules;

import android.content.Context;

import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Dagger2DemoAppApplication mAppApplication;

    public ApplicationModule(Dagger2DemoAppApplication application) {
        this.mAppApplication = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mAppApplication.getApplicationContext();
    }
    
    // UserSessionRepositoryImpl must be singleton (and not UserScope) since it can be accessed by the app even when there is no user logged in, since
    // we need to check whether user was logout the last time app run or not.
    // It is also accessed by the activities when user wants to make a logout
    @Provides
    @Singleton
    UserSessionRepositoryImpl provideUserSessionRepository(Context context) {
        return new UserSessionRepositoryImpl(context);
    }

    @Provides
    @Singleton
    UserRepositoryImpl provideUserRepository() {
        return new UserRepositoryImpl();
    }

}
