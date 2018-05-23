package com.example.avda.spirala1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DohvatiKnjige extends AsyncTask <String, Integer,Void> {


   public interface IDohvatiKnjigeDone{
       public void onDohvatiDone(ArrayList<Knjiga> knjige);
   }

   private IDohvatiKnjigeDone inter;
   private ArrayList<Knjiga> knjige;

   public DohvatiKnjige(IDohvatiKnjigeDone neki){
       this.inter=neki;
   }

    @Override
    protected Void doInBackground(String... strings) {

        knjige=new ArrayList<Knjiga>();

        for(int i=0; i<strings.length;i++) {
            String query = null;
            try {
                query = URLEncoder.encode(strings[i], "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String adresa = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + query + "&maxResults=5";


            try{
                URL url = new URL(adresa);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String rezultat = convertStreamToString(in);
                JSONObject objekat = new JSONObject(rezultat);
                JSONArray items = objekat.getJSONArray("items");
                //
                for(int j = 0; j < items.length(); j++){
                    JSONObject item = items.getJSONObject(j);
                    String novi = item.getString("id");

                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                    String naziv = volumeInfo.getString("title");
                    System.out.println(" ....." + naziv);
                    String opis = volumeInfo.optString("description");

                    String datumObjavljivanja = volumeInfo.optString("publishedDate");
                    int brojStranica = volumeInfo.optInt("pageCount", 0);

                        URL slika = null;
                    ArrayList<Autor> autori = new ArrayList<Autor>();
                    JSONArray autoriJSON = volumeInfo.optJSONArray("authors");


                    if(autoriJSON != null){

                        String novak = autoriJSON.getString(0);
                        Autor autor = new Autor(novak, novi);
                        autori.add(autor);
                        //if(autoriJSON.length() > 1){
                           //// novak = autoriJSON.getString(1);
                            //autor = new Autor(novak, novi);
                           // autori.add(autor);
                       // }
                    }
                    else{
                        autori.add(new Autor("nema", novi));
                    }

                    knjige.add(new Knjiga(novi, naziv, autori,opis, datumObjavljivanja, slika, brojStranica));
                }


            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException prvi){
                prvi.printStackTrace();
            }catch (JSONException drugi){
                drugi.printStackTrace();
            }

        }

        return null;
    }



    public String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
        }catch (IOException e){

        }finally {
            try{
                is.close();
            }catch (IOException e){

            }
        }
        return  sb.toString();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        inter.onDohvatiDone(knjige);
    }
}






