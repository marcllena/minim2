package com.dsa.marc.minim2;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientAPI {

    @GET("/users/{username}")
    Call<Object> obtindreUsuari(@Path("username") String username);

    @GET("/users/{username}/followers")
    Call<List<Object>> obtindreFollowers(@Path("username") String username);
}
