package com.rossana.android.a261117ebookreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by some bitch on 16/12/2017.
 */

class MyLivroAdapter extends ArrayAdapter {
    //Variaveis da classe
    Context mContext;
    int mLayoutForEachNote;
    ArrayList<MyLivro> mLivros;

    //Construtores
    public MyLivroAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MyLivro> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutForEachNote = resource;
        mLivros = objects;
    } //MyNoteAdapter

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater helper = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = helper.inflate(mLayoutForEachNote, parent, false);


        TextView tvTituloLivro, tvNomeAutor;
        tvTituloLivro = (TextView) convertView.findViewById(R.id.idTvTituloLivro);
        tvNomeAutor = (TextView) convertView.findViewById(R.id.idTvAutor);

        int iSlot = position;
        MyLivro livro = mLivros.get(iSlot);

        tvTituloLivro.setText(livro.getTitulo());
        tvNomeAutor.setText(livro.getAutor());
        return convertView;
    }//getView
} //MyLivroAdapter