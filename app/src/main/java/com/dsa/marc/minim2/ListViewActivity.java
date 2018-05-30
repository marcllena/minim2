package com.dsa.marc.minim2;

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

public class ListViewActivity extends AppCompatActivity {
    Object user;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Intent intent = getIntent();
        user= (Object) intent.getSerializableExtra("Usuari");
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textViewFolowing = (TextView)findViewById(R.id.textViewFollowing);
        TextView textViewRepos = (TextView)findViewById(R.id.textViewRepos);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewAvatar);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
       /*textViewFolowing.setText("Following: "+String.valueOf(user.getFollowing()));
        textViewRepos.setText("Repositories: "+String.valueOf(user.getPublicRepos()));
        Picasso.get().load(Uri.parse(user.getAvatarUrl())).into(imageView);
        obtindreFollowers(user.getLogin());
        */
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void obtindreFollowers(String username){
        ClientAPI clientAPI = Globals.getInstance().getclientRetrofit();
        Call<List<Object>> callUsers = clientAPI.obtindreFollowers(username);
        callUsers.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> resposta) {
                if (resposta.isSuccessful()) {
                    List<Object> users=resposta.body();
                    mostrarFollowers(users);
                }
                else{
                    Log.d("onResponse", "onResponse. Code" + Integer.toString(resposta.code()));
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                // log error here since request failed
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d("Request: ", "error loading API" + t.toString());
            }
        });
    }
    public void mostrarFollowers(List<Object> followers){
        ArrayAdapter adapter = new ArrayAdapter(ListViewActivity.this,followers);
        ListView listView = (ListView) findViewById(R.id.list);
        //Per mostrar el titol sobre un ListView
        /*TextView textView = new TextView(FollowersActivity.this);
        textView.setText("FOLLOWERS");
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        listView.addHeaderView(textView);*/
        listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dades.setUsuariSeleccionat(position);
                Intent intent = new Intent(MainActivity.this, UsuariActivity.class);
                startActivity(intent);
            }
        });*/
    }
}

