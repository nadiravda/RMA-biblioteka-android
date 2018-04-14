package com.example.avda.spirala1;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaKnjigaAkt extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_knjiga_akt);
        Intent myIntent = getIntent();
        final ArrayList<Knjiga> knjige = myIntent.getParcelableArrayListExtra("listaKnjiga");
        final ArrayList<Knjiga> kopija =new ArrayList<>();
        String nazivKategorie = myIntent.getStringExtra("nazivKategorije");
        for(int i=0;i<knjige.size();i++){
            if(knjige.get(i).getKategorijaKnjige().equals(nazivKategorie)){
                kopija.add(knjige.get(i));
            }
        }
        final ListView listaKnjiga = (ListView) findViewById(R.id.listaKnjiga);
        AdapterKnjiga adapterKnjiga = new AdapterKnjiga(this,R.layout.izgled_liste_knjiga,kopija);
        listaKnjiga.setAdapter(adapterKnjiga);
        for(int i=0;i<listaKnjiga.getLastVisiblePosition()-listaKnjiga.getFirstVisiblePosition();i++){
            if(kopija.get(i).getObojena().equals("plava"))
                listaKnjiga.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.blue));
        }
        adapterKnjiga.notifyDataSetChanged();
        Button povratak = (Button) findViewById(R.id.dPovratak);
        povratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(ListaKnjigaAkt.this,KategorijeAkt.class);
                myIntent.putExtra("listaKnjiga",knjige);
                myIntent.putExtra("aktivnost","listaKnjiga");
                if(myIntent.resolveActivity(getPackageManager())!=null){
                    ListaKnjigaAkt.this.startActivity(myIntent);
                }
            }
        });
        listaKnjiga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                knjige.get(i).setObojena("plava");
                kopija.get(i).setObojena("plava");
                listaKnjiga.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });
    }
}
