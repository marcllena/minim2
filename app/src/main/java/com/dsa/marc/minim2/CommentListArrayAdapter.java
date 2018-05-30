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
public class CommentListArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Comment> llista;

    public CommentListArrayAdapter(Context context, List<Comment> llista) {
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
        View rowView = inflater.inflate(R.layout.llistacomments, null, true);
        //Operem cada fila
        TextView textViewUser = (TextView) rowView.findViewById(R.id.user);
        TextView textViewMessage = (TextView) rowView.findViewById(R.id.message);
        Comment comment = llista.get(position);
        textViewUser.setText(comment.getUser());
        textViewMessage.setText(comment.getMessage());
        return rowView;
    }
}



