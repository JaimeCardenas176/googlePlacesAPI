package com.example.jaime.weatherplaces.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.jaime.weatherplaces.APIs.GooglePlacesAPI;
import com.example.jaime.weatherplaces.APIs.GooglePlacesServiceGenerator;
import com.example.jaime.weatherplaces.model.Prediction;
import com.example.jaime.weatherplaces.model.PredictionResult;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by jaime on 20/02/2018.
 */

public class GoogleAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULT = 10;
    private Context mContext;
    private List<Prediction> resultlist = new ArrayList<Prediction>();

    public GoogleAutoCompleteAdapter(Context mContext){

    }

    @Override
    public int getCount() {
        return resultlist.size();
    }

    @Override
    public Prediction getItem(int position) {
        return resultlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        //rescatamos las sugerencias y las pintamos
        TextView text = convertView.findViewById(android.R.id.text1);
        Prediction p = getItem(position);
        text.setText(p.getDescription());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null){
                    List<Prediction> resultados = findCities(constraint);

                    if ( resultados != null){
                        filterResults.values=resultados;
                        filterResults.count=resultados.size();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0){
                    resultlist = (List<Prediction>) results.values;
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }
        };
    }
    private List<Prediction> findCities(CharSequence text) {
        List<Prediction> result = null;

        //Generar el servicio
        GooglePlacesAPI api = GooglePlacesServiceGenerator.createService(GooglePlacesAPI.class);
        //Obtener la petición
        Call<PredictionResult> call = api.autoComplete(text.toString());

        //call.enqueue();
        try {
            Response<PredictionResult> response = call.execute();

            if (response.isSuccessful()) {
                if ("todo OK".equalsIgnoreCase(response.body().getStatus()))
                    result = response.body().getPredictions();
            }


        } catch (IOException e) {

            e.printStackTrace();
        }


        return result;
    }
}
