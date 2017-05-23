package com.motondon.dagger2_demoapp.application;

import android.app.Application;
import android.util.Log;

import com.motondon.dagger2_demoapp.di.components.ApplicationComponent;
import com.motondon.dagger2_demoapp.di.components.DaggerApplicationComponent;
import com.motondon.dagger2_demoapp.di.components.DaggerUserComponent;
import com.motondon.dagger2_demoapp.di.components.UserComponent;
import com.motondon.dagger2_demoapp.di.modules.ApplicationModule;
import com.motondon.dagger2_demoapp.di.modules.UserModule;
import com.motondon.dagger2_demoapp.model.user.UserModel;

public class Dagger2DemoAppApplication extends Application {
    private static final String TAG = Dagger2DemoAppApplication.class.getSimpleName();

    private ApplicationComponent applicationComponent;
    private UserComponent userComponent;
    
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");

        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        Log.d(TAG, "initializeInjector()");

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    /**
     * Called after a successful login request
     * 
     * @param user
     */
	public void createUserComponent(UserModel user) {
        Log.d(TAG, "createUserComponent() - user:" + user);

        userComponent = DaggerUserComponent
            .builder()
            .applicationComponent(getApplicationComponent()) // Each component dependency and module without a visible default constructor must be set explicitly
            .userModule(new UserModule(user)) // Each component dependency and module without a visible default constructor must be set explicitly
            .build();
	}
	
	/**
	 * Called when user is being logged out
	 * 
	 */
	public void releaseUserComponent() {
        Log.d(TAG, "releaseUserComponent()");

        // Here we should release all references our component holds (e.g.: a database handler, objects in memory, etc)
		userComponent = null;
	}
	
	public UserComponent getUserComponent() {
		return userComponent;
	}
}
