package com.dsa.marc.minim2;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientAPI {

    @GET("/books")
    Call<List<Book>> obtindreLlibres();

    @GET("/books/{id}")
    Call<Book> obtindreLlibre(@Path("id") String id);

    @POST("/books")
    Call<String> afagirLlibre(@Body Book book);
}
