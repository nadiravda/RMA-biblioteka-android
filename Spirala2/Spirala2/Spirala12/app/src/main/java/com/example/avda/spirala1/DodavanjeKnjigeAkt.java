package com.example.avda.spirala1;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DodavanjeKnjigeAkt extends AppCompatActivity {
    EditText imeAutora;
    EditText nazivKnjige;
    ImageView slika;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje_knjige_akt);
        Button nadjiSliku = (Button) findViewById(R.id.dNadjiSliku);
        Button ponisti = (Button) findViewById(R.id.dPonisti);
        Button upisiKnjigu = (Button) findViewById(R.id.dUpisiKnjigu);
        imeAutora = (EditText) findViewById(R.id.imeAutora);
        nazivKnjige = (EditText) findViewById(R.id.nazivKnjige);
        slika = (ImageView) findViewById(R.id.naslovnaStr);
        nadjiSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        ArrayList<String> kategorije = getIntent().getStringArrayListExtra("listaKategorija");
        final Spinner kategorijaKnjige = (Spinner) findViewById(R.id.sKategorijaKnjige);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, kategorije);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        kategorijaKnjige.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ponisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(DodavanjeKnjigeAkt.this,KategorijeAkt.class);
                if(myIntent.resolveActivity(getPackageManager())!=null){
                    DodavanjeKnjigeAkt.this.startActivity(myIntent);
                }
            }
        });
        upisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Knjiga novaKnjiga = new Knjiga(imeAutora.getText().toString(),nazivKnjige.getText().toString(),kategorijaKnjige.getSelectedItem().toString(),"crvena");
                Intent myIntent = new Intent(DodavanjeKnjigeAkt.this,KategorijeAkt.class);
                myIntent.putExtra("dodanaKnjiga",true);
                myIntent.putExtra("novaKnjiga",novaKnjiga);
                if(myIntent.resolveActivity(getPackageManager())!=null){
                    DodavanjeKnjigeAkt.this.startActivity(myIntent);
                }
            }
        });

    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
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
        nazivKnjige=(EditText)findViewById(R.id.nazivKnjige);
        String sNazivKnjige=nazivKnjige.getText().toString();
        try {
            outputStream=openFileOutput(sNazivKnjige, Context.MODE_PRIVATE);
            getBitmapFromUri(data.getData()).compress(Bitmap.CompressFormat.JPEG,90,outputStream);
            outputStream.close();
            slika.setImageBitmap(BitmapFactory.decodeStream(openFileInput(sNazivKnjige)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
