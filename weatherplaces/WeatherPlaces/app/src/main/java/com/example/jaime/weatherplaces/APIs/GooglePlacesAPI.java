package com.example.jaime.weatherplaces.APIs;

import com.example.jaime.weatherplaces.model.DetailsResult;
import com.example.jaime.weatherplaces.model.PredictionResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jaime on 20/02/2018.
 */

public interface GooglePlacesAPI {
        //https://maps.googleapis.com/maps/api/place/autocomplete/output?parameters
    @GET("maps/api/place/autocomplete/json?type=(cities)")
    Call<PredictionResult> autoComplete(@Query("input") String texto);
    @GET("maps/api/place/details/json")
    Call<DetailsResult> getPlaceDetails(@Query("placeid") String text);

}
