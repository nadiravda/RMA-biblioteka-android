package com.example.avda.spirala1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avda on 30/03/2018.
 */

public class AdapterKategorija extends ArrayAdapter<String> {
    int resource;
    public AdapterKategorija(Context context, int _resource, ArrayList<String> objects) {
        super(context, _resource, objects);
        resource=_resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout newView;
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        }
        else {
            newView = (LinearLayout)convertView;
        }
        String kategorija = getItem(position);
        TextView nazivKategorije = (TextView) newView.findViewById(R.id.nazivKategorije);
        nazivKategorije.setText(kategorija);
        return newView;
    }
}
