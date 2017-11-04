package sadyrkul.aigerim.tmdb;

import android.graphics.Bitmap;

import java.io.Serializable;


public class Film {
    public int id;
    double vote_average;
    String title;
    String poster_path;
    String original_title;
    int [] genres;
    String overview;
    String release_date;
    Bitmap poster;

    public Film(int id, double vote_average, String title, String poster_path, String original_title, int [] genres,
                String overview, String release_date, Bitmap poster){
        this.id =  id;
        this.vote_average = vote_average ;
        this.title = title ;
        this.poster_path = poster_path ;
        this.original_title = original_title ;
        //genre
        int length = genres.length;
        this.genres = new int[length];
        System.arraycopy( genres, 0, this.genres, 0, length );

        this.overview = overview ;
        this.release_date = release_date ;
        this.poster = poster;
    }

    public void copy(Film film){
        this.id =  film.id;
        this.vote_average = film.vote_average ;
        this.title = film.title ;
        this.poster_path = film.poster_path ;
        this.original_title = film.original_title ;

        this.overview = film.overview ;
        this.release_date = film.release_date ;

        int length = film.genres.length;
        this.genres = new int[length];
        System.arraycopy( film.genres, 0, this.genres, 0, length);
    }
}
