package com.example.jaime.weatherplaces.APIs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaime on 02/03/2018.
 */

public class DamApiServiceGenerator {
  private static final String URL_BASE =  "https://damweatherapi-qaxfhigruj.now.sh/api/v1/";

  OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .connectTimeout(1, TimeUnit.MINUTES)
          .readTimeout(30, TimeUnit.SECONDS)
          .writeTimeout(30, TimeUnit.SECONDS)
          .build();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();



  //.baseUrl("http://10.0.2.2:3000/")?????



    private static HttpLoggingInterceptor interceptorBody =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  public static <S> S createService( Class<S> serviceClass) {
    if (!httpClient.interceptors().contains(interceptorBody)) {
      httpClient.addInterceptor(interceptorBody);
    }
    builder.client(httpClient.build());
    builder.client(httpClient.connectTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            .readTimeout(1,TimeUnit.MINUTES)
            .build());
    retrofit = builder.build();
    return retrofit.create(serviceClass);
  }
}



