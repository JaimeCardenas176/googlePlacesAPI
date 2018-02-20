package com.example.jaime.filtrosplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaime on 20/02/18.
 */

public class MyAdapterDefinitivo extends BaseAdapter implements Filterable {

    private static final int MAX_RESULT = 10;
    private Context mContext;
    private List<Prediction> results = new ArrayList<Prediction>();

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.text)).setText(getItem(i).getAlgo());
        return view;

    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filttResult = new FilterResults();

                if (charSequence != null){
                    //realizamos la búsqueda;
                }return filttResult;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    private List<Prediction> findCities(String text){
        List<Prediction> result = null;
        // generar el servicio
        MyAdapterDefinitivo dapter = ServiceGenerator.createService(MyAdapterDefinitivo.class);
        //obtener la petición
        Call<Prediction> call = adapter

        try{
           Response<Prediction>
        }

    }

}
