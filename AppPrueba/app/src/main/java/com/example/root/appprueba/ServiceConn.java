package com.example.root.appprueba;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by why on 19/07/17.
 */

public interface ServiceConn {

    @FormUrlEncoded
    @POST("/UpdateLuz")
    void updateLuz(@Field("ID") int id, @Field("Estado") int estado , Callback<Boolean> callback);

    @FormUrlEncoded
    @POST("/UpdateTimbre")
    void updateTimbre(@Field("ID") int id, @Field("Estado") int estado ,Callback<Boolean> callback);

    @FormUrlEncoded
    @POST("/UpdateTemperatura")
    void updateTemperatura(@Field("ID") int id, @Field("Estado") int estado ,Callback<Boolean> callback);

    @FormUrlEncoded
    @POST("/UpdatePuerta")
    void updatePuerta(@Field("ID") int id, @Field("Estado") int estado, Callback<Boolean> callback);

    @FormUrlEncoded
    @POST("/GetTimbre")
    void getTimbre(@Field("ID") int id, Callback<Boolean> callback);

    @FormUrlEncoded
    @POST("/GetTemperatura")
    void getTemperatura(@Field("ID") int id, Callback<Boolean> callback);

}
