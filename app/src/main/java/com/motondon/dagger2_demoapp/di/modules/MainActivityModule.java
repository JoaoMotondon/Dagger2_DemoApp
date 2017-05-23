package com.motondon.dagger2_demoapp.di.modules;

import android.app.Activity;

import com.motondon.dagger2_demoapp.di.scopes.PerActivityBeforeUserLoginScope;
import com.motondon.dagger2_demoapp.presentation.presenter.user.UserLoginPresenter;
import com.motondon.dagger2_demoapp.presentation.presenter.user.UserLoginPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private Activity mActivity;

    public MainActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivityBeforeUserLoginScope
    UserLoginPresenter provideUserLoginPresenter(UserLoginPresenterImpl userLoginPresenter) {
        return userLoginPresenter;
    }
}
