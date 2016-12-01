package br.com.neon.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NeonApi {
    @GET("GenerateToken")
    Call<String> generateToken(@Query("nome") String nome, @Query("email") String email);
}
