package com.motondon.dagger2_demoapp.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.model.user.UserSession;
import com.motondon.dagger2_demoapp.presentation.presenter.user.UserLoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginFragment extends Fragment implements UserLoginPresenter.View {

    public static final String TAG = UserLoginFragment.class.getSimpleName();

    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.password) EditText inputPassword;

    @Inject
    public UserLoginPresenter userLoginPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        View root = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, root);

        // Request UserLoginActivity to inject dependencies on this class.
        ((UserLoginActivity)getActivity()).getComponent().inject(this);

        // Hide keyboard on startup
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return root;
    }

    @OnClick(R.id.btnLogin)
    public void onUserLoginButtonClick() {
        Log.d(TAG, "onUserLoginButtonClick()");

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // login user
        loginUser(email, password);
    }

    private void loginUser(final String email, final String password) {
        Log.d(TAG, "loginUser()  - email: " + email);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.w(TAG, "loginUser() - invalid email address: " + email);
            inputEmail.setError("Enter a valid email address");

        } else if (password.isEmpty()) {
            inputPassword.setError("Password cannot be empty");

        } else {
            Log.d(TAG, "loginUser() - Email: " + email + " - Password: " + password);

            // Hide keyboard prior the request
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            userLoginPresenter.loginUser(email, password, this);
        }
    }

    private void makeText(final String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show());
    }

    @Override
    public void userLoginSuccessfully(UserModel user) {
        Log.d(TAG, "userLoginSuccessfully() - user: " + user.getName());

        try {

            String name = user.getName();

            Log.d(TAG, "userLoginSuccessfully() - User " + name + " was successful logged-in on the system. Saving its session for future usage.");

            // Now that user has just logged-in on the system, create an UserSession object which will be stored in the repository (preferences)
            // and also stored in the UserSessionComponent (DI) in order to be used when needed (instead of request it from the repository all the time).
            UserSession userSession = new UserSession();
            userSession.setEmail(user.getEmail());
            userSession.setLoggedIn(true);

            // UserLoginPresenter was injected by the ApplicationComponent DI injector
            // Request to the UserSessionRepository to store just logged-in user session.
            userLoginPresenter.updateUserSession(userSession);
            
            // Also create a UserComponent. This will live while user is logged on the system.
            Log.d(TAG, "userLoginSuccessfully() - Creating user component for user: " + user.getName());
            ((Dagger2DemoAppApplication)getActivity().getApplication()).createUserComponent(user);

            // Launch TaskListActivity
            Intent intent = new Intent(getContext(), TaskListActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            Log.e(TAG, "userLoginSuccessfully() - Error while processing server response: " + e.getMessage());
            makeText("Unknown error. Please, try again.");
        }
    }

    @Override
    public void requestFailure(String errorMessage) {
        Log.e(TAG, "requestFailure() - Error while validating login: " + errorMessage);
        makeText(errorMessage);
    }
}
