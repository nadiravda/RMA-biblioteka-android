package com.example.avda.spirala1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaFragmentHoriz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaFragmentHoriz extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AdapterKategorija adapter2;
    private ArrayList<String> kategorije = new ArrayList<>();
    private ArrayList<Knjiga> knjige = new ArrayList<>();
    private ArrayList<Autor> autoriLista = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ListaFragmentHoriz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaFragmentHoriz.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaFragmentHoriz newInstance(String param1, String param2) {
        ListaFragmentHoriz fragment = new ListaFragmentHoriz();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        final View iv=inflater.inflate(R.layout.fragment_lista_fragment_horiz,container, false);
        final Button pretraga = (Button) iv.findViewById(R.id.dPretraga);
        Button dodajKnjigu = (Button) iv.findViewById(R.id.dDodajKnjigu);
        final Button dodajKategoriju = (Button) iv.findViewById(R.id.dDodajKategoriju);
        Button autori = (Button) iv.findViewById(R.id.dAutori);
        final EditText tekstPretrage = (EditText) iv.findViewById(R.id.tekstPretraga);
        Button buttonKategorije = (Button) iv.findViewById(R.id.dKategorije);
        buttonKategorije.setText(R.string.dKategorije);
        pretraga.setText(R.string.dPretraga);
        dodajKnjigu.setText(R.string.dDodajKnjigu);
        dodajKategoriju.setText(R.string.dDodajKategoriju);
        autori.setText(R.string.dAutori);
        if(getArguments() != null && getArguments().containsKey("listaKategorija") && getArguments().containsKey("listaKnjiga") && getArguments().containsKey("listaAutora")) {
            kategorije=getArguments().getStringArrayList("listaKategorija");
            knjige= getArguments().getParcelableArrayList("listaKnjiga");
            autoriLista = getArguments().getParcelableArrayList("listaAutora");
        }
        final AdapterKategorija adapter = new AdapterKategorija(getActivity(),R.layout.izgled_liste_kategorija,kategorije);
        final AdapterAutor adapterAutor = new AdapterAutor(getActivity(),R.layout.izgled_liste_autori,autoriLista);
        buttonKategorije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listaKategorija = (ListView) iv.findViewById(R.id.listaKategorija);
                listaKategorija.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pretraga.setVisibility(View.VISIBLE);
                dodajKategoriju.setVisibility(View.VISIBLE);
                tekstPretrage.setVisibility(View.VISIBLE);
                listaKategorija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        FragmentManager fm= getFragmentManager();
                        KnjigaFragmentHoriz fz=new KnjigaFragmentHoriz();
                        Bundle arg=new Bundle();
                        arg.putStringArrayList("listaKategorija",kategorije);
                        arg.putString("kategorija",listaKategorija.getItemAtPosition(i).toString());
                        fz.setArguments(arg);
                        fm.beginTransaction().replace(R.id.horizOstalo,fz).addToBackStack(null).commit();
                    }
                });
            }
        });
        dodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listaKategorija = (ListView) iv.findViewById(R.id.listaKategorija);
                String tekst = tekstPretrage.getText().toString();
                kategorije.add(tekst);
                adapter.clear();
                adapter2 = funkcija();
                listaKategorija.setAdapter(adapter2);
                tekstPretrage.setText("");
                dodajKategoriju.setEnabled(false);
            }
        });
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
        dodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm= getFragmentManager();
                DodavanjeKnjigeFragment fz=new DodavanjeKnjigeFragment();
                Bundle arg=new Bundle();
                arg.putStringArrayList("listaKategorija",kategorije);
                fz.setArguments(arg);
                fm.beginTransaction().replace(R.id.fragmenti,fz).addToBackStack(null).commit();
            }
        });
        autori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listaKategorija = (ListView) iv.findViewById(R.id.listaKategorija);
                listaKategorija.setAdapter(adapterAutor);
                adapterAutor.notifyDataSetChanged();
                pretraga.setVisibility(View.GONE);
                dodajKategoriju.setVisibility(View.GONE);
                tekstPretrage.setVisibility(View.GONE);
                listaKategorija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        FragmentManager fm=getFragmentManager();
                        KnjigeFragment fz=new KnjigeFragment();
                        Bundle arg=new Bundle();
                        arg.putParcelableArrayList("listaKnjiga",knjige);
                        Autor temp = (Autor) listaKategorija.getItemAtPosition(i);
                        arg.putParcelable("autor",temp);
                        fz.setArguments(arg);
                        fm.beginTransaction().replace(R.id.horizOstalo,fz).addToBackStack(null).commit();
                    }
                });
            }
        });
        return iv;
    }
    AdapterKategorija funkcija(){
        return new AdapterKategorija(getActivity(),R.layout.izgled_liste_kategorija,kategorije);
    }
}
