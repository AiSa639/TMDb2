package sadyrkul.aigerim.tmdb;

import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FilmsListAdapter extends RecyclerView.Adapter<FilmsListAdapter.FilmHolder> {

    private Listener listener;
    int[] id;
    String[] poster_path;
    String[] original_title;
    Bitmap[] bitmap;

    public FilmsListAdapter(int[] id, String[] poster_path, String[] original_title, Bitmap[] bitmap){
        this.id = id;
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.bitmap = bitmap;
    }

    @Override
    public FilmsListAdapter.FilmHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_films, parent,false);
        return new FilmHolder(cv);
    }

    public static class FilmHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
         public FilmHolder(CardView v){
             super(v);
             cardView = v;
         }
    }

    public static interface Listener{
        public void onClick(int id);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(FilmHolder holder, final int position){
        CardView cardView = holder.cardView;
        TextView filmName = (TextView)cardView.findViewById(R.id.film_name);
        filmName.setText(original_title[position]);

        ImageView filmImage = (ImageView)cardView.findViewById(R.id.film_image);
        filmImage.setImageBitmap(bitmap[position]);
        //обработка щелчка
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null){
                    listener.onClick(id[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return  20;
    }


}
