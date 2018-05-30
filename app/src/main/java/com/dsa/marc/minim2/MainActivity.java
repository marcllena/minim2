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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LlistaClick(View view) {
        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        startActivity(intent);
    }

    public void AfagirClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
        startActivity(intent);
    }

    public void AboutClick(View view) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
        dlgAlert.setMessage("App elaborada per Marc Llena. Em faltaría fer el Splash Screen, i el Navigation Drawer");
        dlgAlert.setTitle("Informació");
        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

}

