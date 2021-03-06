package br.com.neon.rest;

import java.util.List;

import br.com.neon.model.TransferHistory;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NeonApi {

    @GET("GenerateToken")
    Call<String> generateToken(@Query("nome") String name,
                               @Query("email") String email);

    @FormUrlEncoded
    @POST("SendMoney")
    Call<Boolean> sendMoney(@Field("ClienteId") String clientId,
                            @Field("token") String token,
                            @Field("valor") double value);

    @GET("GetTransfers")
    Call<List<TransferHistory>> getTransfers(@Query("token") String token);
}
