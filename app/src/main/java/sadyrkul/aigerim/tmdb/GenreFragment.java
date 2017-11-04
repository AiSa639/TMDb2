package sadyrkul.aigerim.tmdb;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment {
    View rootView;
    ListView genresList;
    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_genre, container, false);


        new myAsyncTask().execute("");

        return rootView;
    }

    private class myAsyncTask extends AsyncTask<String, Void, Boolean>{

        String text="";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected Boolean doInBackground(String... urlString){

            try{
                URL url = new URL("https://api.themoviedb.org/3/genre/movie/list?language=ru-rus&api_key=02da584cad2ae31b564d940582770598");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // все ок
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(inputStreamReader);

                    String output;

                    while ((output = br.readLine()) != null) {
                        text+=output;
                    }
                    return true;
                }
                else {
                    return false;
                    // ошибка
                }
            }
            catch (Exception e){
                return false;
                //
                //System.out.println("\nCATCH!");
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {

                try{
                    JSONObject obj = new JSONObject(text);

                    JSONArray arr = obj.getJSONArray("genres");
                    int arrLength = arr.length();

                    String[] genresNames  = new String[arrLength];
                    int[] genresId  = new int[arrLength];

                    for (int i = 0; i < arrLength; i++)
                    {
                        genresId[i] = Integer.parseInt(arr.getJSONObject(i).getString("id"));
                        genresNames[i] = arr.getJSONObject(i).getString("name");
                    }

                    genresList = (ListView) rootView.findViewById(R.id.genres_list);
                    genresList.setAdapter(new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_list_item_1,
                            genresNames));
                }
                catch (Exception e ){
                }
            }
        }
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
            //Intent intent = new Intent();
        }
    };

}
