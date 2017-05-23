package com.motondon.dagger2_demoapp.di.modules;

import android.app.Activity;

import com.motondon.dagger2_demoapp.di.scopes.PerActivityAfterUserLoginScope;
import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskCreatePresenter;
import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskCreatePresenterImpl;
import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskListPresenter;
import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskListPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivityAfterUserLoginScope
    Activity provideActivity() {
        return activity;
    }

    // ***********************************
    // Provides for Task Presenters

    @Provides
    @PerActivityAfterUserLoginScope
    TaskCreatePresenter provideTaskCreatePresenter(TaskCreatePresenterImpl taskCreatePresenter) {
        return taskCreatePresenter;
    }

    @Provides
    @PerActivityAfterUserLoginScope
    TaskListPresenter provideTaskListPresenter(TaskListPresenterImpl taskListPresenter) {
        return taskListPresenter;
    }
}
