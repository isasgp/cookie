package com.example.cookie;

public class Breed {
    private String PET_BREED;
    private String PET_METHOD;

    public Breed() {
    }

    public Breed(String PET_BREED, String PET_METHOD) {
        this.PET_BREED = PET_BREED;
        this.PET_METHOD = PET_METHOD;
    }

    public String getPET_BREED() {
        return PET_BREED;
    }

    public void setPET_BREED(String PET_BREED) {
        this.PET_BREED = PET_BREED;
    }

    public String getPET_METHOD() {
        return PET_METHOD;
    }

    public void setPET_METHOD(String PET_METHOD) {
        this.PET_METHOD = PET_METHOD;
    }
}
