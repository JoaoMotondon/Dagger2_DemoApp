package com.motondon.dagger2_demoapp.presentation.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.di.components.ActivityComponent;
import com.motondon.dagger2_demoapp.di.components.DaggerActivityComponent;
import com.motondon.dagger2_demoapp.di.modules.ActivityModule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskCreateActivity extends BaseActivity {
    public static final String TAG = TaskCreateActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private ActivityComponent activityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_basic);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setTitle("Create new task");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // See this method for details about why we call it before create ActivityComponent
        checkIfUserComponentStillExists();

        Log.d(TAG, "onCreate() - Building ActivityComponent...");
        activityComponent = DaggerActivityComponent
                .builder()
                .userComponent(((Dagger2DemoAppApplication)getApplication()).getUserComponent())
                .activityModule(new ActivityModule(this))
                .build();
        
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TaskCreateFragment.TAG);
        if (fragment == null) {
            try {
                fragment = new TaskCreateFragment();
                fragment.setArguments(getIntent().getExtras());

            } catch (Exception e) {
            	Log.e(TAG, "onCreate() - Fail when trying to create " + TaskCreateFragment.class.getSimpleName() + " fragment. Error: " + e.getMessage());
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, TaskCreateFragment.TAG).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected()");

        switch (item.getItemId()) {

            // In case user clicks on "home" menu option, return false, so it will be handled by the fragment, which will
            // take some actions.
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityComponent getComponent() {
        return activityComponent;
    }
}
