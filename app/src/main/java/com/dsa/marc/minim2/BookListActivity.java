package com.dsa.marc.minim2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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

public class BookListActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    List<Book> llibres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        obtindreLlibres();

    }

    public void obtindreLlibres(){
        ClientAPI clientAPI = Globals.getInstance().getclientRetrofit();
        Call<List<Book>> callBooks = clientAPI.obtindreLlibres();
        callBooks.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> resposta) {
                if (resposta.isSuccessful()) {
                    llibres=resposta.body();
                    mostrarLlibres();
                }
                else{
                    Log.d("onResponse", "onResponse. Code" + Integer.toString(resposta.code()));
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // log error here since request failed
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d("Request: ", "error loading API" + t.toString());
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(BookListActivity.this);
                dlgAlert.setMessage("Error de connexió, comproba la connexió a internet");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }
    public void mostrarLlibres(){
        BookListArrayAdapter adapter = new BookListArrayAdapter(BookListActivity.this,llibres);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                intent.putExtra("id",llibres.get(position).getId());
                startActivity(intent);
            }
        });
    }
}

