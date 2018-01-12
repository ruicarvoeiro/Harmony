package com.rossana.android.a261117ebookreader;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by some bitch on 16/12/2017.
 */

public class MyLivro {
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
    private AmFiles mFiles;
    private Activity mActivity;

    //Construtores
    public MyLivro(Activity mActivity, File file) {
        this.mFicheiro = file;
        this.mActivity = mActivity;
        mPaginas = new ArrayList<String>();
        mFiles = new AmFiles(mActivity);

        getBasicData();
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

    public void setMetaData(String mMetadata) {
        this.mMetaData = mMetadata;
    } //setAutor

    //Metodos
    public void getContent() {
        getConteudos(mFicheiro.getPath());

        //2º Ir buscar o conteudo e dar às variáveis corretas
    } //getContent

    public void getConteudos(String path) {
        try {
            FileInputStream fin = new FileInputStream(path);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = zin.getNextEntry();

            String nomeDoDiretorio = ze.getName().split("/")[0];
            zin.close();
            fin.close();

            if (mFiles.pastaJaExiste(nomeDoDiretorio))
                preencherLivro(nomeDoDiretorio);
            else
                mFiles.unzip(this, path);
        }catch (Exception e){
            e.toString();
        } //catch
    } //getConteudos

    private void preencherLivro(String nomeDoDiretorio) {
        String metadata = mFiles.getTextoFromFicheiro(nomeDoDiretorio + "/META_INFO");
        addMetadata(metadata);

        File diretorioComOsFicheiros = new File(mActivity.getFilesDir(), nomeDoDiretorio + "/1/");
        for(File ficheiro : diretorioComOsFicheiros.listFiles())
            mPaginas.add(mFiles.getTextoFromFicheiro(nomeDoDiretorio + "/1/" + ficheiro.getName()));
    } //preencherLivro


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

    public void getBasicData(){
        String path = mFicheiro.getPath();
        try {
            FileInputStream fin = new FileInputStream(path);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = zin.getNextEntry();

            String nomeDoDiretorio = ze.getName().split("/")[0];
            zin.close();
            fin.close();

            if (mFiles.pastaJaExiste(nomeDoDiretorio))
                preencherLivro(nomeDoDiretorio);
            else
                mFiles.getMetadata(this, path);
        }catch (Exception e){
            e.toString();
        } //catch
    } //getBasicData
} //MyLivro