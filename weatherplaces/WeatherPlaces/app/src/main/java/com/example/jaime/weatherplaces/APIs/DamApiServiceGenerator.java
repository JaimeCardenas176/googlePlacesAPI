package com.example.jaime.weatherplaces.APIs;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaime on 02/03/2018.
 */

public class DamApiServiceGenerator {
  private final String URL_BASE =  "https://damweatherapi-qaxfhigruj.now.sh/api/v1/";
    //URL base, dominio principal al que se realizan las peticiones
    private static final String BASE_URL = "https://maps.googleapis.com/";


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    private static HttpLoggingInterceptor interceptorBody =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  public static <S> S createService( Class<S> serviceClass) {
    if (!httpClient.interceptors().contains(interceptorBody)) {
      httpClient.addInterceptor(interceptorBody);
    }
    builder.client(httpClient.build());
    retrofit = builder.build();
    return retrofit.create(serviceClass);
  }
}



