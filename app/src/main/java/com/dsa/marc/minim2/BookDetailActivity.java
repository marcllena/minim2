package com.dsa.marc.minim2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    String id;
    Book llibre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Intent intent = getIntent();
        id= intent.getExtras().getString("id");
        obtindreLlibre(id);
    }
    public void obtindreLlibre(String id){
        ClientAPI clientAPI = Globals.getInstance().getclientRetrofit();
        Call<Book> callBook = clientAPI.obtindreLlibre(id);
        callBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> resposta) {
                if (resposta.isSuccessful()) {
                   llibre=resposta.body();
                   mostrarLlibre();
                }
                else{
                    Log.d("onResponse", "onResponse. Code" + Integer.toString(resposta.code()));
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                // log error here since request failed
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d("Request: ", "error loading API" + t.toString());
            }
        });
    }
    public void mostrarLlibre(){
        TextView textViewTitol = (TextView) findViewById(R.id.textViewTitulo);
        TextView textViewAutor = (TextView) findViewById(R.id.textViewAutor);
        TextView textViewRating = (TextView) findViewById(R.id.textViewRating);
        TextView textViewDes= (TextView) findViewById(R.id.textViewDes);
        ImageView imageView=(ImageView) findViewById(R.id.imageView);
        textViewTitol.setText("Titol: "+llibre.getTitle());
        textViewAutor.setText("Autor: "+llibre.getAuthor());
        textViewRating.setText("Rating: "+llibre.getRating());
        if(llibre.getImage()==null) {
            imageView.setImageResource(R.drawable.noimage);
        }
        else
            Picasso.get().load(Uri.parse(llibre.getImage())).into(imageView);

        textViewDes.setMovementMethod(new ScrollingMovementMethod());
        textViewDes.setText(llibre.getDescription());
        if(llibre.getComments()!=null) {
            CommentListArrayAdapter adapter = new CommentListArrayAdapter(BookDetailActivity.this, llibre.getComments());
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
