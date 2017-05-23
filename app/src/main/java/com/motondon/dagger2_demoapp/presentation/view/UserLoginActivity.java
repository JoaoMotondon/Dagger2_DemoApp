package com.motondon.dagger2_demoapp.presentation.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.di.components.DaggerMainActivityComponent;
import com.motondon.dagger2_demoapp.di.components.MainActivityComponent;
import com.motondon.dagger2_demoapp.di.modules.MainActivityModule;

public class UserLoginActivity extends AppCompatActivity {

    public static final String TAG = UserLoginActivity.class.getSimpleName();

    private MainActivityComponent mainActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() - Building MainActivityComponent...");
        mainActivityComponent = DaggerMainActivityComponent
                .builder()
                .applicationComponent(((Dagger2DemoAppApplication)getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(UserLoginFragment.TAG);
        if (fragment == null) {
            try {
                fragment = new UserLoginFragment();
                fragment.setArguments(getIntent().getExtras());

            } catch (Exception e) {
            	Log.e(TAG, "onCreate() - Fail when trying to create " + UserLoginFragment.class.getSimpleName() + " fragment. Error: " + e.getMessage());
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, UserLoginFragment.TAG).commit();
    }

    /**
     * This method provides a way on how fragments can access MainActivityComponent
     *
     * @return
     */
    public MainActivityComponent getComponent() {
        return mainActivityComponent;
    }
}
