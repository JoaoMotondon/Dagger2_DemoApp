package com.motondon.dagger2_demoapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.motondon.dagger2_demoapp.model.user.UserSession;


public class UserSessionRepositoryImpl {
    private static String TAG = UserSessionRepositoryImpl.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    private Editor editor;
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ServerLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";

    private UserSession userSession;

    public UserSessionRepositoryImpl(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserSession(UserSession userSession) {
        Log.d(TAG, "setUserSession() - Begin");

        editor.putBoolean(KEY_IS_LOGGEDIN, userSession.isLoggedIn());
        editor.putString(KEY_USER_EMAIL, userSession.getEmail());

        // commit changes
        editor.commit();

        // Also save it in the local UserSession variable.
        this.userSession = userSession;

        Log.d(TAG, "setUserSession() - User session modified");
    }

    public UserSession getUserSession() {
        Log.d(TAG, "getUserSession() - Begin");

        // If UserSession is requested prior to setUserSession() method is called. Create a new object based on
        // the preferences
        if (userSession == null) {
            userSession = new UserSession();
            userSession.setLoggedIn(pref.getBoolean(KEY_IS_LOGGEDIN, false));
            userSession.setEmail(pref.getString(KEY_USER_EMAIL, ""));
        }

        return this.userSession;
    }

    public Boolean  isUserLoggedIn() {
        Log.d(TAG, "isUserLoggedIn() - Begin");

        Boolean isLoggedIn = pref.getBoolean(KEY_IS_LOGGEDIN, false);

        return isLoggedIn;
    }
}
