package APIs;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaime on 20/02/2018.
 */

public class GooglePlacesServiceGenerator {

    //URL base, dominio principal al que se realizan las peticiones
    private static final String BASE_URL = "https://maps.googleapis.com/";

    //"constructor" del objeto Retrofit
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    //"iniciamos" el objeto retrofit
    private static Retrofit retrofit = builder.build();

    //interceptor para ver los datos del Body de la respuesta
    private static HttpLoggingInterceptor interceptorBody =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    //cliente HTTP
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass){
        if(!httpClient.interceptors().contains(interceptorBody)){
            httpClient.addInterceptor(interceptorBody);

            //interceptor anónimo
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl urlOriginal = original.url();

                    //aqui estamos tuneando la urloriginal para añadirle nuestro apiKey
                    // asi como otras preferencias como el lenguaje o el tipo de búsqueda
                    HttpUrl url = urlOriginal.newBuilder()
                            .addQueryParameter("key", "AIzaSyAvRiAzqtvyNOn6WqQcyCZmP_rhewEwbUs")
                            .addQueryParameter("language","es")
                            .addQueryParameter("type","(cities)")
                            .build();
                    //montamos url de la petición
                    Request.Builder requestBuilder = original.newBuilder().url(url);

                    //petición
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }


}
