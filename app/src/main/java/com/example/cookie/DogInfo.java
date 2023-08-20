package com.example.cookie;

import java.util.Calendar;

public class DogInfo {
    private String pet_name;
    private String pet_gender;
    private String pet_neuter;
    private Calendar pet_birth;
    private String pet_breed;
    private String walk_time;
    private String walk_place;

    public DogInfo() {
        this(null, null, null, null, null ,null, null);
    }

    public DogInfo(String pet_name, String pet_gender, String pet_neuter, Calendar pet_birth, String pet_breed, String walk_time, String walk_place) {
        this.pet_name = pet_name;
        this.pet_gender = pet_gender;
        this.pet_neuter = pet_neuter;
        this.pet_birth = pet_birth;
        this.pet_breed = pet_breed;
        this.walk_time = walk_time;
        this.walk_place = walk_place;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_gender() {
        return pet_gender;
    }

    public void setPet_gender(String pet_gender) {
        this.pet_gender = pet_gender;
    }

    public String getPet_neuter() {
        return pet_neuter;
    }

    public void setPet_neuter(String pet_neuter) {
        this.pet_neuter = pet_neuter;
    }

    public Calendar getPet_birth() {
        return pet_birth;
    }

    public void setPet_birth(Calendar pet_birth) {
        this.pet_birth = pet_birth;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getWalk_time() {
        return walk_time;
    }

    public void setWalk_time(String walk_time) {
        this.walk_time = walk_time;
    }

    public String getWalk_place() {
        return walk_place;
    }

    public void setWalk_place(String walk_place) {
        this.walk_place = walk_place;
    }
}
