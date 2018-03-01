package com.example.jaime.weatherplaces;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaime.weatherplaces.APIs.GooglePlacesAPI;
import com.example.jaime.weatherplaces.APIs.GooglePlacesServiceGenerator;
import com.example.jaime.weatherplaces.APIs.OpenWeatherAPI;
import com.example.jaime.weatherplaces.APIs.OpenWeatherServiceGenerator;
import com.example.jaime.weatherplaces.Utilities.DelayAutoCompleteTextView;
import com.example.jaime.weatherplaces.Utilities.GoogleAutoCompleteAdapter;
import com.example.jaime.weatherplaces.model.DetailsResult;
import com.example.jaime.weatherplaces.model.Prediction;
import com.example.jaime.weatherplaces.model.PredictionResult;
import com.example.jaime.weatherplaces.model.currentWeather.WeatherInfo;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


    private Double latitud, longitud;

    //elementos del layout
    private TextView nombreCiudad, fecha, estado, latitudLongitud, maxima, minima;
    private ImageView foto, iconMax, iconMin;
    private DelayAutoCompleteTextView autoCompleteTextView;


    private OnFragmentInteractionListener mListener;

    public static CurrentWeatherFragment newInstance(){
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        autoCompleteTextView = view.findViewById(R.id.delayAutoCompleteTextView);
        autoCompleteTextView.setAdapter(new GoogleAutoCompleteAdapter(getContext()));

        nombreCiudad = view.findViewById(R.id.nombreCiudad);
        fecha = view.findViewById(R.id.fecha);
        estado = view.findViewById(R.id.estado);
        latitudLongitud = view.findViewById(R.id.latitudLongitud);

        foto = view.findViewById(R.id.foto);
        iconMax = view.findViewById(R.id.iconMax);
        iconMin = view.findViewById(R.id.iconMin);
        maxima = view.findViewById(R.id.maxima);
        minima = view.findViewById(R.id.minima);

        final GooglePlacesAPI googlePlacesApi = GooglePlacesServiceGenerator.createService(GooglePlacesAPI.class);
        final OpenWeatherAPI openWeatherApi = OpenWeatherServiceGenerator.createService(OpenWeatherAPI.class);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Prediction prediction = (Prediction) autoCompleteTextView.getAdapter().getItem(i);
                final Call<DetailsResult> callPlaces = googlePlacesApi.getPlaceDetails(prediction.getPlaces_id());
                getFragmentManager().beginTransaction().detach(CurrentWeatherFragment.this).attach(CurrentWeatherFragment.this).commit();
                callPlaces.enqueue(new Callback<DetailsResult>() {
                    @Override
                    public void onResponse(Call<DetailsResult> call, Response<DetailsResult> response) {
                        if (response.isSuccessful()){
                            DetailsResult preRes = response.body();
                            latitud = preRes.getResult().getGeometry().getLocation().getLat();
                            longitud = preRes.getResult().getGeometry().getLocation().getLng();
                            latitudLongitud.setText(String.format("%f, %f",latitud, longitud));


                           /* if (preRes.getResult().getPhotos() != null) {
                                if (!preRes.getResult().getPhotos().isEmpty()) {
                                    String photo_url = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=ae48797f317a02e51e943fa3961983c7&photoreference=%s", preRes.getResult().getPhotos().get(0).getUrlFoto());
                                    Picasso.with(getContext())
                                            .load(photo_url)
                                            .into(foto);
                                }

                            }*/

                                mListener.actualizaCoord(latitud, longitud);

                                Call<WeatherInfo> callCurrentWeather = openWeatherApi.currentWeahterByCoord(latitud,longitud);

                                callCurrentWeather.enqueue(new Callback<WeatherInfo>() {
                                    @Override
                                    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                                        if (response.isSuccessful()){
                                            WeatherInfo current = response.body();
                                            nombreCiudad.setText(current.getName());
                                            maxima.setText(current.getMain().getTempMax().toString()+"ºC");
                                            minima.setText(current.getMain().getTempMin().toString()+"ºC");
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
