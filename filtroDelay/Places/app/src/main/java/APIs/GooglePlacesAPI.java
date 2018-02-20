package APIs;

import model.PredictionResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jaime on 20/02/2018.
 */

public interface GooglePlacesAPI {

    @GET("maps/api/place/autocomplete/json")
    Call<PredictionResult> autoComplete(@Query("input")String texto);
}
