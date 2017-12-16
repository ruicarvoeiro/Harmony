package com.rossana.android.a261117ebookreader;

/**
 * Created by some bitch on 16/12/2017.
 */

public class Livro {
    ///////Variaveis da classe
    private String mTitulo;
    private String mAutor;
    private String mData;
    private String mConteudo;

    //////Construtores
    public Livro(String titulo, String autor, String data){
        this.mTitulo = titulo;
        this.mAutor = autor;
        this.mData = data;
    } //Livro

    public Livro(String titulo, String autor, String data, String conteudo){
        this(titulo, autor, data);
        this.mConteudo = conteudo;
    } //Livro

    //////Acessores


    public String getmTitulo() {
        return mTitulo;
    }

    public void setmTitulo(String mTitulo) {
        this.mTitulo = mTitulo;
    }

    public String getmAutor() {
        return mAutor;
    }

    public void setmAutor(String mAutor) {
        this.mAutor = mAutor;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public String getmConteudo() {
        return mConteudo;
    }

    public void setmConteudo(String mConteudo) {
        this.mConteudo = mConteudo;
    }

} //Livro
