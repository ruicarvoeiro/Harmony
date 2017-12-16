package com.rossana.android.a261117ebookreader;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by some bitch on 16/12/2017.
 */

class MyLivroAdapter<Livro> extends ArrayAdapter<Livro>{
    public MyLivroAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
    /*Ver MyNoteAdapter*/
}
