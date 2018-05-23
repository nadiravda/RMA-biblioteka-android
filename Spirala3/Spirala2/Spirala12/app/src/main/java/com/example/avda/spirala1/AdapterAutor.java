package com.example.avda.spirala1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutor extends ArrayAdapter<Autor> {
    int _resource;
    public AdapterAutor(@NonNull Context context, int resource, @NonNull ArrayList<Autor> objects) {
        super(context, resource, objects);
        _resource=resource;
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
            li.inflate(_resource, newView, true);
        }
        else {
            newView = (LinearLayout)convertView;
        }
        Autor autor =  getItem(position);
        TextView ime = (TextView) newView.findViewById(R.id.imeAutora);
        TextView prezime = (TextView) newView.findViewById(R.id.prezimeAutora);
        TextView brojKnjiga = (TextView) newView.findViewById(R.id.brojKnjiga);
        ime.setText(autor.getIme());
        prezime.setText(autor.getPrezime());
        String tekst = String.valueOf(autor.getBrojKnjiga());
        brojKnjiga.setText(tekst);
        return newView;
    }
}
