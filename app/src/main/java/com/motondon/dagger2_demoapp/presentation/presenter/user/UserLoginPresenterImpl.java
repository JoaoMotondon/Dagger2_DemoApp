package com.motondon.dagger2_demoapp.presentation.presenter.user;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.UserException;
import com.motondon.dagger2_demoapp.domain.interactor.UserLoginInteractor;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.model.user.UserSession;

import javax.inject.Inject;

public class UserLoginPresenterImpl implements UserLoginPresenter {
    private static final String TAG = UserLoginPresenterImpl.class.getSimpleName();

    @Inject
    UserRepositoryImpl userRepository;

    @Inject
    UserSessionRepositoryImpl userSessionRepository;

    // We need to explicitly add this constructor in order to add @Inject dagger annotation, otherwise Dagger will not know how to construct it.
    @Inject
    public UserLoginPresenterImpl() {
        Log.d(TAG, "ctor()");
    }

    @Override
    public void loginUser(String email, String password, UserLoginPresenter.View view) {
        Log.d(TAG, "loginUser() - email: " + email);

        UserLoginInteractor userLoginInteractor = new UserLoginInteractor(userRepository);

        try {
            // If interactor cannot validate user/password, it will throw an exception. .
            UserModel userModel = userLoginInteractor.execute(email, password);
            Log.d(TAG, "loginUser() - user: " + userModel.getName() + " successfully logged");

            view.userLoginSuccessfully(userModel);

        } catch (UserException e) {
            Log.e(TAG, "loginUser() - Failure while trying to login user: " + email + ": " + e.getMessage());
            view.requestFailure(e.getMessage());

        } catch (Exception e) {
            Log.e(TAG, "loginUser() - Exception while trying to login user: " + email + ": " + e.getMessage());
            view.requestFailure(e.getMessage());
        }
    }

    @Override
    public void updateUserSession(UserSession userSession) {
        userSessionRepository.setUserSession(userSession);
    }
}
