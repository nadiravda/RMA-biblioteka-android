package com.example.avda.spirala1;

import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentOnline extends Fragment implements  DohvatiKnjige.IDohvatiKnjigeDone{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Knjiga> noveKnjige;
    ArrayList<String> naziviKnjiga;
     View iv;
     Spinner sRez;
     Spinner sKat;
     Button dTrci;
     Button dDodaj;
     EditText upit;
     ArrayList<String> kategorijee;
     ArrayAdapter<String>  adapterKategorije;
     ArrayAdapter<String> adapterNazivi;
    private OnFragmentInteractionListener mListener;

    public FragmentOnline() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentOnline newInstance(ArrayList<String> kategorije) {
        FragmentOnline fragment = new FragmentOnline();
        Bundle args = new Bundle();
        args.putStringArrayList("kategorije", kategorije);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kategorijee = getArguments().getStringArrayList("kategorije");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        iv = inflater.inflate(R.layout.fragment_online, container, false);

        sRez= (Spinner) iv.findViewById(R.id.sRezultat);
        sKat= (Spinner) iv.findViewById(R.id.sKategorije);
        dTrci=(Button) iv.findViewById(R.id.dRun);
        dDodaj=(Button) iv.findViewById(R.id.dAdd);
        upit =(EditText) iv.findViewById(R.id.textUpit);

        noveKnjige = new ArrayList<>();

        naziviKnjiga = new ArrayList<>();
        adapterNazivi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line , naziviKnjiga);
        sRez.setAdapter(adapterNazivi);
        adapterNazivi.notifyDataSetChanged();
        adapterKategorije = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line , kategorijee);
        sKat.setAdapter(adapterKategorije);



        Button vratiSe= (Button) iv.findViewById(R.id.dPovratak);
        vratiSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                ListaFragment fz=new ListaFragment();
                fm.beginTransaction().replace(R.id.fragmenti,fz).addToBackStack(null).commit();

            }
        });

        dDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        dTrci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upisano = upit.getText().toString();
                if(upisano.contains(";")){
                    int countWords = upisano.length() - upisano.replace(";", "").length() + 1;
                    String[] nizStringova = new String[countWords];
                    int lastIdx = -1;
                    for(int i = 0; i < countWords; i++){
                        if(i != countWords - 1){
                            nizStringova[i] = upisano.substring(lastIdx + 1, upisano.indexOf(';', lastIdx + 1));
                            lastIdx = upisano.indexOf(';', lastIdx + 1);
                        }
                        else{
                            nizStringova[i] = upisano.substring(lastIdx + 1);
                        }

                    }

                    (new DohvatiKnjige((DohvatiKnjige.IDohvatiKnjigeDone)FragmentOnline.this)).execute(nizStringova);
                }
                else{
                    new DohvatiKnjige(((DohvatiKnjige.IDohvatiKnjigeDone)FragmentOnline.this)).execute(upisano);
                }
            }
        });


        return iv;
    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDohvatiDone(ArrayList<Knjiga> knjige) {
        noveKnjige=knjige;

        for(int i=0;i<knjige.size();i++){
            naziviKnjiga.add(noveKnjige.get(i).getNaziv());

        }

        adapterNazivi.notifyDataSetChanged();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
