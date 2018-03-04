package com.example.jaime.weatherplaces.APIs;

import android.media.Image;

import com.example.jaime.weatherplaces.model.ResponseDam;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by jaime on 02/03/2018.
 */

public interface DamApi {
    @Multipart
    @POST("auth/register")
    public Call<ResponseDam> doRegister(
            @Part("email") String email,
            @Part("password") String password,
            @Part("displayName") String displayName,
            @Part("photo") Image imagen
    );
    @POST("auth/login")
    public Call<ResponseDam> doLogin(@Query("email") String email, @Query("password") String password);



}
