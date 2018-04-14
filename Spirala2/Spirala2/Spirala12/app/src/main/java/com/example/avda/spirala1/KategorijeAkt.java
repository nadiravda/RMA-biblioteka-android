package com.example.avda.spirala1;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class KategorijeAkt extends AppCompatActivity {
    final ArrayList<String> kategorije = new ArrayList<String>();
    AdapterKategorija adapter2;
    Boolean siriL=false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);
        Intent intentic = getIntent();
        ArrayList<Knjiga> knjige = new ArrayList<Knjiga>();
        ArrayList<Autor> autori = new ArrayList<>();
        autori.add(new Autor("Nadir","Avdagic",1));
        autori.add(new Autor("Branko","Copic",0));
        kategorije.add("Sci-fi"); kategorije.add("Basna"); kategorije.add("Bajka");
        if(intentic.hasExtra("dodanaKnjiga")){
            knjige.add((Knjiga) intentic.getParcelableExtra("novaKnjiga"));
        }
        if(intentic.hasExtra("aktivnost")){
            knjige=intentic.getParcelableArrayListExtra("listaKnjiga");
        }
        else{
            knjige.add(new Knjiga("Nadir Avdagic","Zelena","Bajka","nije"));
            knjige.add(new Knjiga("Neko Nekic","Plava","Sci-fi","nije"));
            knjige.add(new Knjiga("Afan Secic","Crvena","Bajka","plava"));
        }
        /*Button pretraga = (Button) findViewById(R.id.dPretraga);
        Button dodajKnjigu = (Button) findViewById(R.id.dDodajKnjigu);
        final Button dodajKategoriju = (Button) findViewById(R.id.dDodajKategoriju);
        final ListView listaKategorija = (ListView) findViewById(R.id.listaKategorija);
        final EditText tekstPretrage = (EditText) findViewById(R.id.tekstPretraga);
        dodajKategoriju.setEnabled(false);
        final AdapterKategorija adapter = new AdapterKategorija(this,R.layout.izgled_liste_kategorija,kategorije);

        listaKategorija.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        pretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tekst = tekstPretrage.getText().toString();
                adapter.getFilter().filter(tekst);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(adapter.getCount()==0){
                    dodajKategoriju.setEnabled(true);
                }
            }
        });
        dodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tekst = tekstPretrage.getText().toString();
                kategorije.add(tekst);
                adapter.clear();
                adapter2 = funkcija();
                listaKategorija.setAdapter(adapter2);
                tekstPretrage.setText("");
                dodajKategoriju.setEnabled(false);
            }
        });
        dodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(KategorijeAkt.this,DodavanjeKnjigeAkt.class);
                myIntent.putStringArrayListExtra("listaKategorija",kategorije);
                if(myIntent.resolveActivity(getPackageManager())!=null){
                    KategorijeAkt.this.startActivity(myIntent);
                }
            }
        });
        final ArrayList<Knjiga> finalKnjige = knjige;
        listaKategorija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(KategorijeAkt.this, ListaKnjigaAkt.class);
                myIntent.putExtra("listaKnjiga", finalKnjige);
                myIntent.putExtra("nazivKategorije",kategorije.get(i));
                if(myIntent.resolveActivity(getPackageManager())!=null){
                    KategorijeAkt.this.startActivity(myIntent);
                }
            }
        });*/
        final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FrameLayout okreni=(FrameLayout)findViewById(R.id.horizontalno);
        if(okreni!=null){
            siriL=true;
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ListaFragmentHoriz fg ;
            fg = new ListaFragmentHoriz();
            Bundle argumenti=new Bundle();
            argumenti.putStringArrayList("listaKategorija",kategorije);
            argumenti.putParcelableArrayList("listaKnjiga",knjige);
            argumenti.putParcelableArrayList("listaAutora",autori);
            fg.setArguments(argumenti);
            fm.beginTransaction().replace(R.id.fragmenti,fg).commit();
            KnjigaFragmentHoriz defaultni= new KnjigaFragmentHoriz();
            Bundle arg=new Bundle();
            defaultni.setArguments(arg);
            fm.beginTransaction().replace(R.id.horizOstalo,defaultni).addToBackStack(null).commit();
        }
        else{
            siriL=false;
            ListaFragment lista=new ListaFragment();
            Bundle argumenti = new Bundle();
            argumenti.putStringArrayList("listaKategorija",kategorije);
            argumenti.putParcelableArrayList("listaKnjiga",knjige);
            argumenti.putParcelableArrayList("listaAutora",autori);
            lista.setArguments(argumenti);
            fm.beginTransaction().replace(R.id.fragmenti,lista).commit();
            fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
    AdapterKategorija funkcija(){
        return new AdapterKategorija(this,R.layout.izgled_liste_kategorija,kategorije);
    }
}
