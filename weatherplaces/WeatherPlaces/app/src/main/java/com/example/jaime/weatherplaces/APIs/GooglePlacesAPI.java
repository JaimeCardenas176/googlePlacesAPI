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

    @GET("maps/api/place/autocomplete/json/?type=(cities)&language=es")
    Call<PredictionResult> autoComplete(@Query("input") String texto);
    @GET("maps/api/place/autocomplete/json")
    Call<DetailsResult> getPlaceDetails(@Query("place_id") String text);

}
