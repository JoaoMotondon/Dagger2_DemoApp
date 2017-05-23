package com.motondon.dagger2_demoapp.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.model.user.UserSession;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    UserSessionRepositoryImpl userSessionRepository;

    @Inject 
    UserRepositoryImpl userRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firts request Dagger to inject members in this class
        ((Dagger2DemoAppApplication)getApplication()).getApplicationComponent().inject(this);

        // Now, check whether user was logged-in the last time we run this app. If so, get it from the user repository and skip login process
        // by showing to the user TaskListActivity.
        if (userSessionRepository.isUserLoggedIn()) {
            UserSession userSession = userSessionRepository.getUserSession();

            Log.d(TAG, "onCreate() - detected user: " + userSession.getEmail() + " already logged. Getting userModel object...");

            // Request to the user repository the userModel object related to the user already logged into the system. It will be used to
            // instantiate UserComponent
            UserModel user = userRepository.getUser(userSession.getEmail());

            // Note: If for some reason user got from the preferences does not exist in the database, we should present the login activity instead.
            // This should never happen here, since we are not actually using a database, but in a real application we should take care of it.
            if (user == null) {
                Log.d(TAG, "onCreate() - detected UserModel object for user: " + userSession.getEmail() + " is null. Calling user login activity...");
                startUserLoginActivity();
                return;
            }

            // Now create a UserSessionComponent.
            Log.d(TAG, "Creating user component for user: " + user.getName());
            ((Dagger2DemoAppApplication)getApplication()).createUserComponent(user);

            // And finally show the TaskListActivity
            startTaskListActivity();
        } else {

            // When no user session is available, show the user UserLoginActivity
            startUserLoginActivity();
        }
    }

    private void startTaskListActivity() {
        Log.d(TAG, "startTaskListActivity");

        // Launch TaskListActivity activity
        Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
        startActivity(intent);
    }

    private void startUserLoginActivity() {
        Log.d(TAG, "startUserLoginActivity");

        // Launch UserLoginActivity activity
        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
        startActivity(intent);
    }
}
