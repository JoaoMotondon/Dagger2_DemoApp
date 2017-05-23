package com.motondon.dagger2_demoapp.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.di.components.ActivityComponent;
import com.motondon.dagger2_demoapp.di.components.DaggerActivityComponent;
import com.motondon.dagger2_demoapp.di.modules.ActivityModule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends BaseActivity {
    private static final String TAG = TaskListActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_basic);
        ButterKnife.bind(this);
                		
        setSupportActionBar(mToolbar);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        // See this method for details about why we call it before create ActivityComponent
        checkIfUserComponentStillExists();

        Log.d(TAG, "onCreate() - Building ActivityComponent...");
        activityComponent = DaggerActivityComponent
                .builder()
                .userComponent(((Dagger2DemoAppApplication)getApplication()).getUserComponent())
                .activityModule(new ActivityModule(this))
                .build();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TaskListFragment.TAG);
        if (fragment == null) {
            try {
                fragment = new TaskListFragment();
                fragment.setArguments(getIntent().getExtras());

            } catch (Exception e) {
                Log.e(TAG, "onCreate() - Fail when trying to create " + TaskListFragment.class.getSimpleName() + " fragment. Error: " + e.getMessage());
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, TaskListFragment.TAG).commit();
    }

    /**
     * This method provides a way on how fragments can access ActivityComponent
     *
     * @return
     */
    public ActivityComponent getComponent() {
        return activityComponent;
    }
}
