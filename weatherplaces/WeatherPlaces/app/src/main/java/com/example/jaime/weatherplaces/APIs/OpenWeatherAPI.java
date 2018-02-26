package com.example.jaime.weatherplaces.APIs;


import com.example.jaime.weatherplaces.model.currentWeather.WeatherInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jaime on 21/02/18.
 */

public interface OpenWeatherAPI {
    @GET("data/weather/2.5/weather")
    public Call<WeatherInfo> currentWeahter(@Query("lat") float latitude, @Query("long") float longitude);

    @GET("data/weather/2.5/forecast")
    public Call<WeatherInfo> forecastWeahter(@Query("lat") float latitude, @Query("long") float longitude);



}
