package com.example.jaime.weatherplaces.APIs;


import com.example.jaime.weatherplaces.model.currentWeather.WeatherInfo;
import com.example.jaime.weatherplaces.model.forecastWeather.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jaime on 21/02/18.
 */

public interface OpenWeatherAPI {
    @GET("data/2.5/weather")
    public Call<WeatherInfo> currentWeahterByCoord(@Query("lat") Double latitude, @Query("long") Double longitude);

    @GET("data/2.5/forecast")
    public Call<Forecast> forecastWeahter(@Query("lat") Double latitude, @Query("long") Double longitude);



}
