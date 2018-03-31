package com.example.avda.spirala1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avda on 30/03/2018.
 */

public class Knjiga implements Parcelable {
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
