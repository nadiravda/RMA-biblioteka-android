package com.example.avda.spirala1;

import android.os.Parcel;
import android.os.Parcelable;

public class Autor implements Parcelable{
    private String ime,prezime;

    protected Autor(Parcel in) {
        ime = in.readString();
        prezime = in.readString();
        brojKnjiga = in.readInt();
    }
    public Autor(String ime, String prezime, int brojKnjiga){
        this.ime = ime;
        this.prezime = prezime;
        this.brojKnjiga = brojKnjiga;
    }
    public static final Creator<Autor> CREATOR = new Creator<Autor>() {
        @Override
        public Autor createFromParcel(Parcel in) {
            return new Autor(in);
        }

        @Override
        public Autor[] newArray(int size) {
            return new Autor[size];
        }
    };

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getBrojKnjiga() {
        return brojKnjiga;
    }

    public void setBrojKnjiga(int brojKnjiga) {
        this.brojKnjiga = brojKnjiga;
    }

    private int brojKnjiga;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ime);
        parcel.writeString(prezime);
        parcel.writeInt(brojKnjiga);
    }
}
