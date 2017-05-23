package com.motondon.dagger2_demoapp.domain.interactor;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.UserException;
import com.motondon.dagger2_demoapp.model.user.UserModel;

public class UserLoginInteractor {
    private static final String TAG = UserLoginInteractor.class.getSimpleName();

    private UserRepositoryImpl userRepository;

    public UserLoginInteractor(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel execute(String email, String password) throws UserException {
        Log.d(TAG, "execute() - email: " + email);

        UserModel userModel = userRepository.getUser(email);

        if (userModel == null) {
            throw new UserException("User " + email + " not found");
        } else {

            if (password.equals(userModel.getPassword())) {
                Log.d(TAG, "execute() - User: " + email + " was found.");
                return userModel;
            } else {
                Log.w(TAG, "execute() - Password does not match");
                throw new UserException("Password does not match");
            }
        }
    }
}
