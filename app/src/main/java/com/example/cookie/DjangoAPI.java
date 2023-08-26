package com.example.cookie;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DjangoAPI {

    public static final String API_URL = "http://3.35.85.32:8000";

    @POST("/pet/")
    Call<Pet> post_posts(@Body Pet post);

    @PATCH("/pet/{pk}/")
    Call<Pet> patch_posts(@Path("pk") int pk, @Body Pet post);

    @DELETE("/pet/{pk}/")
    Call<Pet> delete_posts(@Path("pk") int pk);

    @GET("/pet/")
    Call<List<Pet>> get_posts();

    @GET("/pet/{pk}/")
    Call<Pet> get_post_pk(@Path("pk") int pk);
}
