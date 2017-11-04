package sadyrkul.aigerim.tmdb;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabPopularFragment extends Fragment {
    View rootView;
    RecyclerView filmsList;
    LinearLayout mLoadingFooter;
    public static String TYPE = "type";
    public static String POPULAR = "popular";
    public static String SOON_ON_SCREENS = "Soon on screens";
    public static String NOW_IN_CINEMAS = "Now in cinemas";
    public static String TV_SERIES = "Now in cinemas";

    private  Film [] films = new Film[20];
    private  int [] id = new int[20];
    private  String [] poster_path = new String[20];
    private  String [] original_title = new String[20];
    private Bitmap [] bitmap = new Bitmap[20];
    private ProgressBar progressBar;

    public TabPopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab_now, container, false);

        mLoadingFooter = (LinearLayout) inflater.inflate(R.layout.loading, null);

        Bundle bundle = getArguments();

        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        //here is your list array
        String tab = bundle.getString(TYPE);

        if(tab.equals(POPULAR)){
            new FilmsAsyncTask().execute("https://api.themoviedb.org/3/discover/movie?api_key=02da584cad2ae31b564d940582770598&language=ru-rus&sort_by=popularity.desc&page=1");
        }
        else if(tab.equals(SOON_ON_SCREENS)){
            new FilmsAsyncTask().execute("https://api.themoviedb.org/3/movie/upcoming?api_key=02da584cad2ae31b564d940582770598&language=ru-rus&page=1");
        }
        else if(tab.equals(NOW_IN_CINEMAS)){
            new FilmsAsyncTask().execute("https://api.themoviedb.org/3/movie/now_playing?api_key=02da584cad2ae31b564d940582770598&language=ru-rus&page=1");

        }
        return rootView;
    }

    private class FilmsAsyncTask extends AsyncTask<String, Integer, Boolean> {
        TextView test;
        String text="";
        String urlStringAT;
        @Override
        protected void onPreExecute() {
            test = (TextView)rootView.findViewById(R.id.test);
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... urlString){
            urlStringAT = urlString[0];
            try{
                URL url = new URL(urlStringAT);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // все ок
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(inputStreamReader);

                    String output;

                    while ((output = br.readLine()) != null) {
                        text+=output;
                    }
                        try{
                            JSONObject obj = new JSONObject(text);

                            JSONArray arr = obj.getJSONArray("results");
                            int arrLength = arr.length();


                            for (int i = 0; i < arrLength; i++)
                            {
                                id[i] = Integer.parseInt(arr.getJSONObject(i).getString("id"));
                                poster_path[i]  = arr.getJSONObject(i).getString("poster_path");
                                original_title[i]  = arr.getJSONObject(i).getString("original_title");

                                //постер
                                bitmap[i] = getBitmapFromURL("https://image.tmdb.org/t/p/w500"+arr.getJSONObject(i).getString("poster_path"));
                                publishProgress(i);
                                /*
                                //жанры
                                text = arr.getJSONObject(i).getString("genre_ids");
                                text = text.substring(1,text.length()-2);
                                text = text.replaceAll(",", " ");

                                String [] genreStr = text.split(" ");
                                int [] genres = new int[genreStr.length];
                                int t = 0;
                                for (String str : genreStr) {
                                    genres[t++] = Integer.parseInt(str);
                                }


                                films[i] = new Film(Integer.parseInt(arr.getJSONObject(i).getString("id")),
                                        Double.parseDouble(arr.getJSONObject(i).getString("vote_average")),
                                        arr.getJSONObject(i).getString("title"),
                                        arr.getJSONObject(i).getString("poster_path") ,
                                        arr.getJSONObject(i).getString("original_title"),
                                        genres,
                                        arr.getJSONObject(i).getString("overview")  ,
                                        arr.getJSONObject(i).getString("release_date"),
                                        bitmap
                                        );*/

                            }
                        }
                        catch (Exception e ){
                            return false;
                        }
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (Exception e){
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try{
                super.onPostExecute(aBoolean);
                if(aBoolean == true){
                    //test.setText(text);
                    progressBar.setVisibility(View.GONE);

                    filmsList = (RecyclerView)rootView.findViewById(R.id.film_list);
                    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    filmsList.setLayoutManager(gridLayoutManager);

                    final FilmsListAdapter adapter = new FilmsListAdapter(id, poster_path, original_title, bitmap);

                    adapter.setListener(new FilmsListAdapter.Listener(){
                        public void onClick(int id){
                            Intent intent = new Intent(getActivity(), FilmDetail.class);

                            intent.putExtra("id", id);
                            /*intent.putExtra("position", position);

                            intent.putExtra("vote_average", film.vote_average);
                            intent.putExtra("title", film.title);
                            intent.putExtra("poster_path", film.poster_path);
                            intent.putExtra("original_title", film.original_title);
                            intent.putExtra("overview", film.overview);
                            intent.putExtra("release_date", film.release_date);

                            intent.putExtra("poster", film.poster);*/
                            getActivity().startActivity(intent);
                        }
                    });

                    filmsList.setAdapter(adapter);
                }
                else {
                    test.setText("FALSE!");
                }
            }
            catch (Exception e){

            }
        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
