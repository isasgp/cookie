package com.example.cookie;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DogInfoAPI {

    public static final String API_URL = "http://3.35.85.32:8000";

    @POST("/pet/")
    Call<DogInfo> post_posts(@Body DogInfo post);

    @PATCH("/pet/{pk}/")
    Call<DogInfo> patch_posts(@Path("pk") int pk, @Body DogInfo post);

    @DELETE("/pet/{pk}/")
    Call<DogInfo> delete_posts(@Path("pk") int pk);

    @GET("/pet/")
    Call<List<DogInfo>> get_posts();

    @GET("/pet/{pk}/")
    Call<DogInfo> get_post_pk(@Path("pk") int pk);
}
