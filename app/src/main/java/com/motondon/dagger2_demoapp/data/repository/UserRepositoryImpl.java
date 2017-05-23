package com.motondon.dagger2_demoapp.data.repository;

import android.util.Log;

import com.motondon.dagger2_demoapp.common.Constants;
import com.motondon.dagger2_demoapp.model.user.UserModel;

import java.util.Hashtable;

public class UserRepositoryImpl {
    private static final String TAG = UserRepositoryImpl.class.getSimpleName();

    private Hashtable<String, UserModel> mUserList = new Hashtable<>();

    public UserRepositoryImpl() {
        Log.d(TAG, "ctor()");

        generateFakeData();
    }

    /**
     * Generate an in-memory list with two users
     *
     */
    private void generateFakeData() {
        Log.d(TAG, "generateFakeData()");

        mUserList.clear();
        mUserList.put(Constants.ADMIN_01_EMAIL, new UserModel(Constants.ADMIN_01_USER_NAME, Constants.ADMIN_01_EMAIL, Constants.ADMIN_01_PASSWORD));
        mUserList.put(Constants.ADMIN_02_EMAIL, new UserModel(Constants.ADMIN_02_USER_NAME, Constants.ADMIN_02_EMAIL, Constants.ADMIN_02_PASSWORD));
    }

    public UserModel getUser(String email) {
        Log.d(TAG, "getUser() - email: " + email);

        if (mUserList.containsKey(email)) {

            UserModel user = mUserList.get(email);
            Log.d(TAG, "getUser() - Found user: " + user.getName() + " for email: " + email);

            return user;
        } else {
            Log.w(TAG, "getUser() - Not found user for email: " + email);

            return null;
        }
    }
}
