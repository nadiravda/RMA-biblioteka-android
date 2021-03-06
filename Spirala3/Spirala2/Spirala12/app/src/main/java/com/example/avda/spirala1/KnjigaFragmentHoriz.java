package com.example.avda.spirala1;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KnjigaFragmentHoriz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KnjigaFragmentHoriz extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public KnjigaFragmentHoriz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KnjigaFragmentHoriz.
     */
    // TODO: Rename and change types and number of parameters
    public static KnjigaFragmentHoriz newInstance(String param1, String param2) {
        KnjigaFragmentHoriz fragment = new KnjigaFragmentHoriz();
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
        View iv = inflater.inflate(R.layout.fragment_knjiga_fragment_horiz, container, false);
        ListView listaKnjiga = (ListView) iv.findViewById(R.id.listaKnjiga);
        ArrayList<String> kategorije = new ArrayList<>();
        ArrayList<Knjiga> knjige = new ArrayList<>();
        ArrayList<Knjiga> finalKnjige = new ArrayList<>();
        Autor autor = null;
        if(getArguments() != null && getArguments().containsKey("listaKategorija") && getArguments().containsKey("kategorija")){
            kategorije = getArguments().getStringArrayList("listaKategorija");
            String kategorija = getArguments().getString("kategorija");
            finalKnjige=knjige;
            for(int i = 0;i<knjige.size();i++){
                if(kategorija!=knjige.get(i).getKategorijaKnjige()){
                    knjige.remove(i);
                    i--;
                }
            }
        }
        if(getArguments() != null && getArguments().containsKey("listaKnjiga") && getArguments().containsKey("autor")){
            knjige = getArguments().getParcelableArrayList("listaKnjiga");
            autor = getArguments().getParcelable("autor");
            finalKnjige=knjige;
            for(int i = 0;i<knjige.size();i++){
                String[] lista = knjige.get(i).imeAutora.split(" ");
                if(lista[0] != autor.getIme() && lista[1] != autor.getPrezime()){
                    knjige.remove(i);
                    i--;
                }
            }
        }

        return iv;
    }

}
