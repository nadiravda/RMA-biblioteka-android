package com.example.avda.spirala1;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Avda on 30/03/2018.
 */

public class Knjiga implements Parcelable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Autor> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autor> autori) {
        this.autori = autori;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(String datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public URL getSlika() {
        return slika;
    }

    public void setSlika(URL slika) {
        this.slika = slika;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }

    String id;
    String naziv;
    ArrayList<Autor> autori;
    String opis;
    String datumObjavljivanja;
    URL slika;
    int brojStranica;

    public Knjiga(String id, String naziv, ArrayList<Autor> autori, String opis, String datumObjavljivanja, URL slika, int brojStranica) {
        this.id = id;
        this.naziv = naziv;
        this.autori = autori;
        this.opis = opis;
        this.datumObjavljivanja = datumObjavljivanja;
        this.slika = slika;
        this.brojStranica = brojStranica;
    }

    String imeAutora;
    String nazivKnjige;
    String kategorijaKnjige;
    String obojena;
    public String getObojena() {
        return obojena;
    }

    public void setObojena(String obojena) {
        this.obojena = obojena;
    }


    public Knjiga(String ime, String naziv, String kategorija, String boja){
        imeAutora=ime;
        nazivKnjige=naziv;
        kategorijaKnjige=kategorija;
        obojena=boja;
    }

    protected Knjiga(Parcel in) {
        imeAutora = in.readString();
        nazivKnjige = in.readString();
        kategorijaKnjige = in.readString();
        obojena = in.readString();
    }

    public static final Creator<Knjiga> CREATOR = new Creator<Knjiga>() {
        @Override
        public Knjiga createFromParcel(Parcel in) {
            return new Knjiga(in);
        }

        @Override
        public Knjiga[] newArray(int size) {
            return new Knjiga[size];
        }
    };

    public String getImeAutora() {
        return imeAutora;
    }

    public void setImeAutora(String imeAutora) {
        this.imeAutora = imeAutora;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public String getKategorijaKnjige() {
        return kategorijaKnjige;
    }

    public void setKategorijaKnjige(String kategorijaKnjige) {
        this.kategorijaKnjige = kategorijaKnjige;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getImeAutora());
        parcel.writeString(getNazivKnjige());
        parcel.writeString(getKategorijaKnjige());
        parcel.writeString(getObojena());
    }
}
