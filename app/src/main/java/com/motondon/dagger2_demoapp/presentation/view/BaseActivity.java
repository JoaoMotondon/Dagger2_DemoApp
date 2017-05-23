package com.motondon.dagger2_demoapp.presentation.view;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.di.components.UserComponent;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.model.user.UserSession;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Inject
    UserSessionRepositoryImpl userSessionRepository;

    @Inject
    UserRepositoryImpl userRepository;

    /**
     * If this app is killed by the system while it is in background, system will recreate the last visible activity , but not UserComponent (since it is
     * supposed to be created either after a successfully login or by the MainActivity when user launches the app and an user was previously logged). So,
     * if we try to create the ActivityComponent, since it depends on the UserComponent, we will get an NEP.
     *
     * This is where this method comes in handy. It will check whether UserComponent really exists, and if not, it will create it. After that, activities can
     * safely create ActivityComponent.
     *
     * We can find some notes about it at: http://frogermcs.github.io/building-userscope-with-dagger2/
     *
     */
    protected void checkIfUserComponentStillExists() {
        Log.d(TAG, "checkIfUserComponentStillExists()");

        UserComponent userComponent = ((Dagger2DemoAppApplication) getApplication()).getUserComponent();
        if (userComponent == null) {

            ((Dagger2DemoAppApplication)getApplication()).getApplicationComponent().inject(this);

            Log.i(TAG, "onCreate() - Detected userComponent is null. This mostly happens when system killed this app while it was in background. Recreating it...");

            UserSession userSession = userSessionRepository.getUserSession();

            // Request to the user repository the userModel object related to the user already logged into the system. It will be used to
            // instantiate UserComponent
            UserModel user = userRepository.getUser(userSession.getEmail());
            Log.d(TAG, "Creating user component for user: " + user.getName());

            // And finally create UserComponent
            ((Dagger2DemoAppApplication) getApplication()).createUserComponent(user);
        }
    }
}
