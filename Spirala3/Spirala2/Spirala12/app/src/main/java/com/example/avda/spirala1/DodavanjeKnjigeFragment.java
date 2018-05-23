package com.example.avda.spirala1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DodavanjeKnjigeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DodavanjeKnjigeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText imeAutora;
    EditText nazivKnjige;
    ImageView slika;
    View iv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DodavanjeKnjigeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DodavanjeKnjigeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DodavanjeKnjigeFragment newInstance(String param1, String param2) {
        DodavanjeKnjigeFragment fragment = new DodavanjeKnjigeFragment();
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
        // Inflate the layout for this fragment
         iv =  inflater.inflate(R.layout.fragment_dodavanje_knjige, container, false);
        Button nadjiSliku = (Button) iv.findViewById(R.id.dNadjiSliku);
        Button ponisti = (Button) iv.findViewById(R.id.dPonisti);
        final Spinner spinner = (Spinner) iv.findViewById(R.id.sKategorijaKnjige);
        Button upisiKnjigu = (Button) iv.findViewById(R.id.dUpisiKnjigu);
        imeAutora = (EditText) iv.findViewById(R.id.imeAutora);
        nazivKnjige = (EditText) iv.findViewById(R.id.nazivKnjige);
        slika = (ImageView) iv.findViewById(R.id.naslovnaStr);
        nadjiSliku.setText(R.string.dNadjiSliku);
        ponisti.setText(R.string.dPonisti);
        upisiKnjigu.setText(R.string.dUpisiKnjigu);
        nadjiSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        ArrayList<Knjiga> knjige = new ArrayList<>();
        final ArrayList<Knjiga> finalKnjige1 = knjige;
        upisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Knjiga knjiga = new Knjiga(imeAutora.getText().toString(),nazivKnjige.getText().toString(),spinner.getSelectedItem().toString(),"nije obojena");
                finalKnjige1.add(knjiga);
            }
        });
        ArrayList<String> kategorije = new ArrayList<>();
        if(getArguments()!= null && getArguments().containsKey("listaKategorija") && getArguments().containsKey("listaKnjiga")){
            kategorije=getArguments().getStringArrayList("listaKategorija");
            knjige = getArguments().getParcelableArrayList("listaKnjiga");
        }
        final ArrayList<String> finalKategorije = kategorije;
        ponisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                ListaFragment fz=new ListaFragment();
                Bundle arg=new Bundle();
                arg.putStringArrayList("listaKategorija", finalKategorije);
                arg.putParcelableArrayList("listaKnjiga", finalKnjige1);
                fz.setArguments(arg);
                fm.beginTransaction().replace(R.id.fragmenti,fz).addToBackStack(null).commit();
            }
        });
        return iv;
    }
    public interface OnItemClick{
        public void OnItemClickDodajKnjigu();
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //spasava sliku u internu mem aplikacije
        FileOutputStream outputStream;
        nazivKnjige= (EditText) iv.findViewById(R.id.nazivKnjige);
        String sNazivKnjige=nazivKnjige.getText().toString();
        try {
            outputStream= getActivity().openFileOutput(sNazivKnjige, Context.MODE_PRIVATE);
            getBitmapFromUri(data.getData()).compress(Bitmap.CompressFormat.JPEG,90,outputStream);
            outputStream.close();
            slika.setImageBitmap(BitmapFactory.decodeStream(getActivity().openFileInput(sNazivKnjige)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
