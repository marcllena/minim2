package com.dsa.marc.minim2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void followersClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextUsername);
        String username = editText.getText().toString();
        if (username.equals("") ) {//Mirem que hagi emplenat el camp
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Emplena el nom d'usuari");
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
        //Fem la consulta
        obtindreUsuari(username);

    }

    public void obtindreUsuari(String username){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        ClientAPI clientRetrofit = Globals.getInstance().getclientRetrofit();
        Call<Object> callUser = clientRetrofit.obtindreUsuari(username);
        callUser.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> resposta) {
                if (resposta.isSuccessful()) {
                    //Obtindre imatges e implementar progress bar
                    Object user=resposta.body();
                    Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                    //intent.putExtra("Usuari", user);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    startActivity(intent);
                }
                else if(resposta.code()==404){
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage("No hi ha cap usuari amb aquest nom");
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
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // log error here since request failed
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d("Request: ", "error loading API" + t.toString());
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
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
}

