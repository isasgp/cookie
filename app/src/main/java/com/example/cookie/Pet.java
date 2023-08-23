package com.example.cookie;

public class Pet {
    private int PET_ID;
    private String PET_NAME;
    private String PET_GENDER;
    private String PET_NEUTER;
    private String PET_BIRTH;
    private String PET_BREED;
    private String WALK_TIME;
    private  String USER_ID;
    private String WALK_PLACE;

    public Pet() {
    }

    public Pet(String PET_NAME, String PET_GENDER, String PET_NEUTER, String PET_BIRTH, String PET_BREED, String WALK_TIME, String USER_ID, String WALK_PLACE) {
        this.PET_NAME = PET_NAME;
        this.PET_GENDER = PET_GENDER;
        this.PET_NEUTER = PET_NEUTER;
        this.PET_BIRTH = PET_BIRTH;
        this.PET_BREED = PET_BREED;
        this.WALK_TIME = WALK_TIME;
        this.WALK_PLACE = WALK_PLACE;
        this.USER_ID = USER_ID;
    }

    public String getPET_NAME() {
        return PET_NAME;
    }

    public void setPET_NAME(String PET_NAME) {
        this.PET_NAME = PET_NAME;
    }

    public String getPET_GENDER() {
        return PET_GENDER;
    }

    public void setPET_GENDER(String PET_GENDER) {
        this.PET_GENDER = PET_GENDER;
    }

    public String getPET_NEUTER() {
        return PET_NEUTER;
    }

    public void setPET_NEUTER(String PET_NEUTER) {
        this.PET_NEUTER = PET_NEUTER;
    }

    public String getPET_BIRTH() {
        return PET_BIRTH;
    }

    public void setPET_BIRTH(String PET_BIRTH) {
        this.PET_BIRTH = PET_BIRTH;
    }

    public String getPET_BREED() {
        return PET_BREED;
    }

    public void setPET_BREED(String PET_BREED) {
        this.PET_BREED = PET_BREED;
    }

    public String getWALK_TIME() {
        return WALK_TIME;
    }

    public void setWALK_TIME(String WALK_TIME) {
        this.WALK_TIME = WALK_TIME;
    }

    public String getWALK_PLACE() {
        return WALK_PLACE;
    }

    public void setWALK_PLACE(String WALK_PLACE) {
        this.WALK_PLACE = WALK_PLACE;
    }

    public String getUSER_ID() { return USER_ID; }

    public void setUSER_ID(String USER_ID) { this.USER_ID = USER_ID; }

    public int getPET_ID() {
        return PET_ID;
    }

    public void setPET_ID(int PET_ID) {
        this.PET_ID = PET_ID;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "PET_NAME='" + PET_NAME + '\'' +
                ", PET_GENDER='" + PET_GENDER + '\'' +
                ", PET_NEUTER='" + PET_NEUTER + '\'' +
                ", PET_BIRTH='" + PET_BIRTH + '\'' +
                ", PET_BREED='" + PET_BREED + '\'' +
                ", WALK_TIME='" + WALK_TIME + '\'' +
                ", WALK_PLACE='" + WALK_PLACE + '\'' +
                ", USER_ID='" + USER_ID + '\'' +
                '}';
    }
}
