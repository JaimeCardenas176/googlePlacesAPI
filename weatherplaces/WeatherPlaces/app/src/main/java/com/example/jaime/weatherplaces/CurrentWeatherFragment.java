package com.example.jaime.weatherplaces;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.weatherplaces.APIs.GooglePlacesAPI;
import com.example.jaime.weatherplaces.APIs.GooglePlacesServiceGenerator;
import com.example.jaime.weatherplaces.APIs.OpenWeatherAPI;
import com.example.jaime.weatherplaces.APIs.OpenWeatherServiceGenerator;
import com.example.jaime.weatherplaces.Utilities.DelayAutoCompleteTextView;
import com.example.jaime.weatherplaces.Utilities.GoogleAutoCompleteAdapter;
import com.example.jaime.weatherplaces.model.DetailsResult;
import com.example.jaime.weatherplaces.model.Prediction;
import com.example.jaime.weatherplaces.model.currentWeather.WeatherInfo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrentWeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrentWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentWeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context ctx;
    Calendar fechaActual = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
    private Double latitud, longitud;

    //elementos del layout
    private TextView nombreCiudad, fecha, estado, latitudLongitud, maxima, minima, humedadContent;
    private ImageView foto, placeImg;
    private DelayAutoCompleteTextView autoCompleteTextView;


    private OnFragmentInteractionListener mListener;

    public static CurrentWeatherFragment newInstance() {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        return fragment;

    }


    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        nombreCiudad = view.findViewById(R.id.nombreCiudad);
        fecha = view.findViewById(R.id.fecha);
        estado = view.findViewById(R.id.estado);
        latitudLongitud = view.findViewById(R.id.latitudLongitud);
        humedadContent = view.findViewById(R.id.humedadContent);
        foto = view.findViewById(R.id.foto);
        placeImg = view.findViewById(R.id.placeImg);
        maxima = view.findViewById(R.id.maxima);
        minima = view.findViewById(R.id.minima);

        final GooglePlacesAPI googlePlacesApi = GooglePlacesServiceGenerator.createService(GooglePlacesAPI.class);
        final OpenWeatherAPI openWeatherApi = OpenWeatherServiceGenerator.createService(OpenWeatherAPI.class);
        autoCompleteTextView = view.findViewById(R.id.delayAutoCompleteTextView);
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.setAdapter(new GoogleAutoCompleteAdapter(getContext()));

        //calculando la posicion gps para cargar unos detalles predeterminados al inicar la app
        LocationManager locationManager;
        ctx = getContext();
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Prediction prediction = (Prediction) autoCompleteTextView.getAdapter().getItem(i);
                final Call<DetailsResult> callPlaces = googlePlacesApi.getPlaceDetails(prediction.getPlace_id());
               // getFragmentManager().beginTransaction().detach(CurrentWeatherFragment.this).attach(CurrentWeatherFragment.this).commit();
                callPlaces.enqueue(new Callback<DetailsResult>() {
                    @Override
                    public void onResponse(Call<DetailsResult> call, Response<DetailsResult> response) {
                        if (response.isSuccessful()){
                            DetailsResult preRes = response.body();
                            latitud = preRes.getResult().getGeometry().getLocation().getLat();
                            longitud = preRes.getResult().getGeometry().getLocation().getLng();
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putFloat("longi", Float.valueOf(String.valueOf(longitud)));
                            editor.putFloat("lati", Float.valueOf(String.valueOf(latitud)));
                            editor.commit();

                            estado.setText(String.format("%f, %f",latitud, longitud));
                            placeImg.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            if (preRes.getResult().getPhotos() != null) {
                                if (!preRes.getResult().getPhotos().isEmpty()) {
                                    String photo_url = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=AIzaSyAxNJnfgm73CooJFmHnRWSrQwQt-S4RV34&photoreference=%s", preRes.getResult().getPhotos().get(0).getUrlFoto());                                    placeImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    Picasso.with(getContext())
                                            .load(photo_url)
                                            .into(placeImg);
                                }

                            }
                                mListener.actualizaCoord(latitud, longitud);

                                Call<WeatherInfo> callCurrentWeather = openWeatherApi.currentWeahterByCoord(latitud,longitud);

                                callCurrentWeather.enqueue(new Callback<WeatherInfo>() {
                                    @Override
                                    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                                        if (response.isSuccessful()){
                                            WeatherInfo current = response.body();
                                            nombreCiudad.setText(current.getName());
                                            fecha.setText(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(fechaActual.get(Calendar.MONTH)+1)+"/"+String.valueOf(fechaActual.get(Calendar.YEAR)));
                                            maxima.setText(current.getMain().getTempMax().toString()+"ºC");
                                            minima.setText(current.getMain().getTempMin().toString()+"ºC");
                                            latitudLongitud.setText(current.getWeather().get(0).getDescription());
                                            humedadContent.setText(current.getMain().getHumidity().toString()+"%");

                                            Picasso.with(getContext()).load("http://openweathermap.org/img/w/"+current.getWeather().get(0).getIcon()+ ".png").into(foto);
                                            Log.d("Retrofit OK!", current.toString());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WeatherInfo> call, Throwable t) {
                                        Log.d("Fallo",t.getMessage());
                                    }
                                });

                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsResult> call, Throwable t) {

                    }
                });

            }
        });

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void actualizaCoord(Double latitud, Double longitud);
    }
}
