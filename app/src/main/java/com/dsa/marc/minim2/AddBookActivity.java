package com.dsa.marc.minim2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void afagirLlibre_Click(View view){
        EditText editTextTitol = (EditText) findViewById(R.id.editTextTitol);
        String titol = editTextTitol.getText().toString();
        EditText editTextAutor = (EditText) findViewById(R.id.editTextAutor);
        String autor = editTextAutor.getText().toString();
        EditText editTextDes = (EditText) findViewById(R.id.editTextDescripcio);
        String descripcio = editTextDes.getText().toString();
        if (titol.equals("")||autor.equals("")||descripcio.equals("")) {//Mirem que hagi emplenat els camp
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Emplena tots els camps");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        Book llibre = new Book();
        llibre.setTitle(titol);
        llibre.setAuthor(autor);
        llibre.setDescription(descripcio);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        afagirLlibre(llibre);
    }

    public void afagirLlibre(Book llibre){
        ClientAPI clientAPI = Globals.getInstance().getclientRetrofit();
        Call<String> callBook = clientAPI.afagirLlibre(llibre);
        callBook.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> resposta) {
                if (resposta.isSuccessful()) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(AddBookActivity.this);
                    dlgAlert.setMessage("Llibre afagit correctament");
                    dlgAlert.setTitle("Operació realitzada");
                    dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                } else if(resposta.code()==204){
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(AddBookActivity.this);
                    dlgAlert.setMessage("No s'ha pogut afagir el llibre");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                else{
                    Log.d("onResponse", "onResponse. Code" + Integer.toString(resposta.code()));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }
}
