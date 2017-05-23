package com.motondon.dagger2_demoapp.presentation.presenter.user;

import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.model.user.UserSession;

public interface UserLoginPresenter {

    interface View {
        void userLoginSuccessfully(UserModel userModel);
        void requestFailure(String errorMessage);
    }
    void loginUser(String email, String password, View view);
    void updateUserSession(UserSession userSession);
}
