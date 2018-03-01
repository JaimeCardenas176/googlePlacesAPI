package com.example.jaime.weatherplaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaime.weatherplaces.ForecastWeatherFragment.OnListFragmentInteractionListener;
import com.example.jaime.weatherplaces.model.forecastWeather.Forecast;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyForecastRecyclerViewAdapter extends RecyclerView.Adapter<MyForecastRecyclerViewAdapter.ViewHolder> {

    private final List<com.example.jaime.weatherplaces.model.forecastWeather.List> mValues;
    //private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyForecastRecyclerViewAdapter(List<com.example.jaime.weatherplaces.model.forecastWeather.List> items, Context context) {
        mValues = items;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.fechaF.setText(holder.mItem.getDtTxt());
        Picasso.with(context)
                .load("http://openweathremap.org/img/w"+holder.mItem.getWeather().get(0).getIcon()+".png")
        .resize(800,800)
        .centerCrop()
        .into(holder.fotoF);

        holder.humedadF.setText(String.valueOf(holder.mItem.getMain().getHumidity()+"% humedad"));
        holder.maximaF.setText(holder.mItem.getMain().getTempMax().toString());
        holder.minimaF.setText(holder.mItem.getMain().getTempMin().toString());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView fechaF, maximaF, minimaF, humedadF;
        public final ImageView fotoF, iconMaxF, iconMinF;
        public com.example.jaime.weatherplaces.model.forecastWeather.List mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            fechaF = view.findViewById(R.id.fechaForecast);
            maximaF = view.findViewById(R.id.maximaForecast);
            minimaF = view.findViewById(R.id.minimaForecast);
            humedadF = view.findViewById(R.id.humedad);
            fotoF=view.findViewById(R.id.fotoForecast);
            iconMaxF = view.findViewById(R.id.iconMaxForecast);
            iconMinF = view.findViewById(R.id.iconMinforecast);


        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
