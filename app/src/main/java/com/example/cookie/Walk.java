package com.example.cookie;

public class Walk {
    private String WALK_PLACE;
    private String WALK_METHOD;

    public Walk() {
    }

    public Walk(String WALK_PLACE, String WALK_METHOD) {
        this.WALK_PLACE = WALK_PLACE;
        this.WALK_METHOD = WALK_METHOD;
    }

    public String getWALK_PLACE() {
        return WALK_PLACE;
    }

    public void setWALK_PLACE(String WALK_PLACE) {
        this.WALK_PLACE = WALK_PLACE;
    }

    public String getWALK_METHOD() {
        return WALK_METHOD;
    }

    public void setWALK_METHOD(String WALK_METHOD) {
        this.WALK_METHOD = WALK_METHOD;
    }
}
