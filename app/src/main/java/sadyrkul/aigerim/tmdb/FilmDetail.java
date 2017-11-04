package sadyrkul.aigerim.tmdb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FilmDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);



        int filmID = getIntent().getExtras().getInt("id");

        new FilmsAsyncTask().execute("https://api.themoviedb.org/3/movie/"+filmID+"?api_key=02da584cad2ae31b564d940582770598");

    }

    private class FilmsAsyncTask extends AsyncTask<String, Void, Boolean> {
        TextView test, titleTV, releaseTV, overviewTV, genreTV;
        Bitmap bitmap;
        String text="";
        String urlStringAT;
        String id="";
        String title="";
        String release_date="";
        String overview="";
        ImageView poster;

        @Override
        protected void onPreExecute() {
            test = (TextView)findViewById(R.id.test);
            titleTV = (TextView)findViewById(R.id.title);
            genreTV = (TextView)findViewById(R.id.genres);
            releaseTV = (TextView)findViewById(R.id.release_date);
            overviewTV = (TextView)findViewById(R.id.overview);
            poster  = (ImageView) findViewById(R.id.poster);

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
                        id = obj.getString("id");
                        title = obj.getString("title");
                        release_date = obj.getString("release_date");
                        overview = obj.getString("overview");
                        bitmap = getBitmapFromURL("https://image.tmdb.org/t/p/w500"+obj.getString("poster_path"));


                        JSONArray arr = obj.getJSONArray("genres");
                        int arrLength = arr.length();

                        text="";
                        for (int i = 0; i < arrLength; i++)
                        {
                            text += arr.getJSONObject(i).getString("name")+", ";
                        }
                        text = text.substring(0,text.length()-3);

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
        protected void onPostExecute(Boolean aBoolean) {
            try{
                super.onPostExecute(aBoolean);
                if(aBoolean == true){
                    titleTV.setText(title);
                    releaseTV.setText(release_date);
                    overviewTV.setText(overview);
                    genreTV.setText(text);
                    poster.setImageBitmap(bitmap);
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
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
