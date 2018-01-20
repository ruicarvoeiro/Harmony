package com.rossana.android.a261117ebookreader;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by some bitch on 16/12/2017.
 */

public class MyLivro implements Serializable {
    //Variaveis da classe
    private String mTitulo;
    private String mAutor;
    private File mFicheiro;
    private String mGenero;
    private ArrayList<String> mPaginas;
    private String mMetaData;
    private String mISBN;
    private int mEdicao;
    private int mAno;

    //Outros objetos
    //private AmFiles mFiles;
    //private Activity mActivity;

    //Construtores
    public MyLivro(File file) {
        this.mFicheiro = file;
        mPaginas = new ArrayList<String>();
    } //MyLivro

    //Acessores
    public String getTitulo() {
        return mTitulo;
    } //getTitulo

    public void setTitulo(String mTitulo) {
        this.mTitulo = mTitulo;
    } //setTitulo

    public String getAutor() {
        return mAutor;
    } //getAutor

    public void setAutor(String mAutor) {
        this.mAutor = mAutor;
    } //setAutor

    public File getFicheiro() {
        return mFicheiro;
    } //getFicheiro

    public void setFicheiro(File livro) {
        this.mFicheiro = livro;
    } //setFicheiro

    public String getGenero() {
        return mGenero;
    } //getAutor

    public void setGenero(String mAutor) {
        this.mGenero = mGenero;
    } //setAutor

    public ArrayList getPaginas(){ return mPaginas;}

    public void setPaginas(ArrayList mCapitulos){ this.mPaginas = mCapitulos;}

    public String getISBN(){
        return mISBN;
    }

    public String getMetaData() {
        return mMetaData;
    } //getAutor

    public File getFile() {
        return mFicheiro;
    } //getAutor

    public void setMetaData(String mMetadata) {
        this.mMetaData = mMetadata;
    } //setAutor


    //Metodos
    public void addPagina(String conteudo, int numeroPagina){
        mPaginas.add(numeroPagina, conteudo);
    } //addPagina

    public void addMetadata(String conteudo){
        String [] valores = conteudo.split("\r\n");
        mISBN = valores[0];
        mTitulo = valores[1];
        mAutor = valores[2];
        mAno = Integer.parseInt(valores[3]);
        mEdicao = Integer.parseInt(valores[4]);
        mGenero = valores[5];
    } //addMetadata

    @Override
    public boolean equals(Object o){
        boolean bOsLivrosSaoIguais = false;
        try{
            MyLivro livro2 = (MyLivro) o;
            bOsLivrosSaoIguais =
                    livro2.getISBN() == getISBN() &&
                            livro2.getAutor() == getAutor() &&
                            livro2.getTitulo() == getTitulo();
        } catch(Exception e) {}
        return bOsLivrosSaoIguais;
    } //equals
} //MyLivro