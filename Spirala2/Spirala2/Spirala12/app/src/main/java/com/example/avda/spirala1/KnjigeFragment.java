package com.example.avda.spirala1;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link KnjigeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KnjigeFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public KnjigeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KnjigeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KnjigeFragment newInstance(String param1, String param2) {
        KnjigeFragment fragment = new KnjigeFragment();
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
        View iv = inflater.inflate(R.layout.fragment_knjige, container, false);
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
        final Autor finalAutor = autor;
        Button povratak = (Button) iv.findViewById(R.id.dPovratak);
        povratak.setText(R.string.dPovratak);
        final android.support.v4.app.FragmentManager fm = getFragmentManager();
        final ArrayList<String> finalKategorije = kategorije;
        final ArrayList<Knjiga> finalKnjige1 = finalKnjige;
        povratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaFragment lista=new ListaFragment();
                Bundle argumenti = new Bundle();
                argumenti.putStringArrayList("listaKategorija", finalKategorije);
                argumenti.putParcelableArrayList("listaKnjiga", finalKnjige1);
                argumenti.putParcelable("autor",finalAutor);
                lista.setArguments(argumenti);
                fm.beginTransaction().replace(R.id.fragmenti,lista).commit();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return iv;
    }
}

