package com.example.jaime.weatherplaces;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaime.weatherplaces.APIs.OpenWeatherAPI;
import com.example.jaime.weatherplaces.APIs.OpenWeatherServiceGenerator;
import com.example.jaime.weatherplaces.model.forecastWeather.Forecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ForecastWeatherFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String LONGITUD="lat";
    public static final String LATITUD="lon";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Forecast pronosticos;
    MyForecastRecyclerViewAdapter adaptador;
    RecyclerView recyclerView;

    private Double mParam1;
    private Double mParam2;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ForecastWeatherFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ForecastWeatherFragment newInstance(Double lat, Double lon) {
        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
        Bundle args = new Bundle();
        args.putDouble(LATITUD,lat);
        args.putDouble(LONGITUD, lon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getDouble(LATITUD);
            mParam2 = getArguments().getDouble(LONGITUD);
        }
    }

    public void refrescar(Double v1, Double v2){
        mParam1 =v1;
        mParam2 =v2;

        OpenWeatherAPI openWeatherAPI = OpenWeatherServiceGenerator.createService(OpenWeatherAPI.class);
        Call<Forecast> forecast = openWeatherAPI.forecastWeahter(mParam1,mParam2);

        forecast.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()){
                    pronosticos = response.body();

                    adaptador = new MyForecastRecyclerViewAdapter(pronosticos.getList(), getActivity());
                    recyclerView.setAdapter(adaptador);
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_list, container, false);
        recyclerView = (RecyclerView) view;


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        refrescar(mParam1,mParam2);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Forecast item);
    }
}
