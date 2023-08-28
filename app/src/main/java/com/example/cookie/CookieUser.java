package com.example.cookie;

public class CookieUser {
    private String USER_ID;
    private int PET_ID;

    public CookieUser() {
    }

    public CookieUser(String USER_ID, int PET_ID) {
        this.USER_ID = USER_ID;
        this.PET_ID = PET_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public int getPET_ID() {
        return PET_ID;
    }

    public void setPET_ID(int PET_ID) {
        this.PET_ID = PET_ID;
    }
}
