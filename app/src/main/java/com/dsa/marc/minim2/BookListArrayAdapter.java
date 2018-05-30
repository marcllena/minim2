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
public class BookListArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Book> llista;

    public BookListArrayAdapter(Context context, List<Book> llista) {
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
        View rowView = inflater.inflate(R.layout.llistabooks, null, true);
        //Operem cada fila
        TextView textViewTitol = (TextView) rowView.findViewById(R.id.titol);
        TextView textViewAutor = (TextView) rowView.findViewById(R.id.autor);
        ImageView imageView=(ImageView) rowView.findViewById(R.id.image);
        Book llibre = llista.get(position);
        textViewTitol.setText(llibre.getTitle());
        textViewAutor.setText(llibre.getAuthor());
        if(llibre.getImage()==null) {
            imageView.setImageResource(R.drawable.noimage);
        }
        else {
            Picasso.get().load(Uri.parse(llibre.getImage())).into(imageView);
        }

        return rowView;
    }
}


