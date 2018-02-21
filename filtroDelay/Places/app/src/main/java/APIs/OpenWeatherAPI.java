package APIs;

import model.currentOpenWeather.WeatherInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jaime on 21/02/18.
 */

public interface OpenWeatherAPI {
    @GET("data/weather/2.5/weather")
    public Call<WeatherInfo> currentWeahter(@Query("q") String cityName);

    @GET("data/weather/2.5/weather")
    public Call<WeatherInfo> currentWeahter(@Query("q") String cityName, String countryCode);



}
