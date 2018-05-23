package com.example.avda.spirala1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avda on 30/03/2018.
 */

public class AdapterKnjiga extends ArrayAdapter<Knjiga> {
    int resource;
    public AdapterKnjiga(@NonNull Context context, int _resource, @NonNull ArrayList<Knjiga> objects) {
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
        Knjiga knjiga = getItem(position);
        TextView autor = (TextView) newView.findViewById(R.id.eAutor);
        TextView naziv = (TextView) newView.findViewById(R.id.eNaziv);
        ImageView slika = (ImageView) newView.findViewById(R.id.eNaslovna);
        autor.setText(knjiga.getNazivKnjige());
        naziv.setText(knjiga.getImeAutora());
        try {
            slika.setImageBitmap(BitmapFactory.decodeStream(getContext().openFileInput(knjiga.getNazivKnjige())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newView;
    }
}
