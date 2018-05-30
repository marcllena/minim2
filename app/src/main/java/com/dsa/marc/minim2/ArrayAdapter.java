package com.dsa.marc.minim2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

//ArrayAdapter per mostrar els elements a la ListView
public class ArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Object> llista;

    public ArrayAdapter(Context context, List<Object> llista) {
        this.context = context;
        this.llista = llista;
    }

    @Override
    public int getCount() {
        return llista.size();
    }

    @Override
    public Object getItem(int position) {
        return llista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.llista, null, true);
        //Operem cada fila
        TextView textViewNom = (TextView) rowView.findViewById(R.id.nom);
        ImageView imageView=(ImageView) rowView.findViewById(R.id.avatar);
        Object usuari = llista.get(position);
        textViewNom.setText("String");
        Picasso.get().load(Uri.parse(usuari.toString())).into(imageView);
        return rowView;
    }
}

