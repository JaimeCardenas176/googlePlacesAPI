package com.example.jaime.weatherplaces.APIs;

import com.example.jaime.weatherplaces.model.damModelsAPI.ResponseAllFiles;
import com.example.jaime.weatherplaces.model.damModelsAPI.ResponseFile;
import com.example.jaime.weatherplaces.model.damModelsAPI.ResponseUser;
import com.example.jaime.weatherplaces.model.damModelsAPI.ResponseUserFiles;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by jaime on 02/03/2018.
 */

public interface DamApi {
    @Multipart
    @POST("auth/register")
    Call<ResponseUser> doRegister(
            @Part("email") String email,
            @Part("password") String password,
            @Part("displayName") String displayName,
            @Part MultipartBody.Part imagen
    );
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseUser> doLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("files/upload")
    Call<ResponseFile> uploadFile(
            @Header("Authorization") String auth,
            @Header("token") String token,
            @Part("coords") String coords,
            @Part("title") String title,
            @Part MultipartBody.Part photo
    );

    @GET("files/")
    Call<ResponseAllFiles> allFiles(
            @Header("Authorization") String auth,
            @Header("token") String token
    );

    @GET("files/mine")
    Call<ResponseUserFiles> userFiles(
            @Header("Authorization") String auth,
            @Header("token") String token
    );
}
