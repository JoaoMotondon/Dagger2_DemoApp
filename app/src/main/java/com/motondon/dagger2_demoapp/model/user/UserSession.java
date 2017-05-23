package com.motondon.dagger2_demoapp.model.user;

import java.io.Serializable;

public class UserSession implements Serializable {

    private boolean loggedIn;
    private String email;

    public UserSession() {
        loggedIn = false;
        email = "";
    }

    public boolean isLoggedIn(){
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
