package com.example.cookie;

import android.app.Application;

public class GlobalVariable extends Application {
    private String USER_ID;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
}
