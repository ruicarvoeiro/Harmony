package com.rossana.android.a261117ebookreader;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by some bitch on 14/12/2017.
 */

public class AmFiles {
    //Constantes
    public static final String EXTENCAO = ".MACOAS";

    //Objetos
    //private Activity mActivity;
    //private AmUtil mUtil;

    //Construtor
    /*public AmFiles(Activity pA) {
        this.mActivity = pA;
        this.mUtil = new AmUtil(pA);
    }//AmUtil*/

    public AmFiles(){} //AmFiles

    ///////////// INICIO PESQUISA DE LIVROS E AFINS /////////////
    public static ArrayList<MyLivro> pesquisaDeLivros() {
        ArrayList<MyLivro> livrosQueCoincidemComAPesquisa = new ArrayList<MyLivro>();
        ArrayList<File> todosOsLivrosExistentes = getAllLivros();
        return livrosQueCoincidemComAPesquisa;
    } //pesquisaDeLivros

    public static void getAllLivrosInDirectory(String nomeDoDiretorio, ArrayList<File> listaDeLivros) {
        File directory = new File(nomeDoDiretorio);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList)
                if (file.isFile()) {
                    if (temAExtencaoCorreta(file.getName()))
                        listaDeLivros.add(file);
                } else if (file.isDirectory())
                    getAllLivrosInDirectory(file.getAbsolutePath(), listaDeLivros);
    } //getAllLivrosInDirectory

    public static ArrayList<File> getAllLivros() {
        String diretorio = Environment.getExternalStoragePublicDirectory("").getPath();

        //TODO: procurar tanto no sdCard, como na memória interna
        //Atualmente só procuro na memória interna

        ArrayList<File> ficheirosQueSaoLivros = new ArrayList<File>();
        getAllLivrosInDirectory(diretorio, ficheirosQueSaoLivros);

        return ficheirosQueSaoLivros;
    } //getAllLivros

    ///////////// FIM PESQUISA DE LIVROS E AFINS /////////////


    ///////////// INICIO DESCOMPRESSORES /////////////
    public static void unzip(String fullPathDoLivro, String destinoDosFicheirosUnzipped) {
        try {
            FileInputStream fin = new FileInputStream(fullPathDoLivro);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory())
                    criaDiretorio(destinoDosFicheirosUnzipped, ze.getName());
                else {
                    FileOutputStream fout = new FileOutputStream(destinoDosFicheirosUnzipped + ze.getName());
                    for (int i = zin.read(); i != -1; i = zin.read())
                        fout.write(i);

                    zin.closeEntry();
                    fout.close();
                } //else
            } //while
            zin.close();
        } catch (Exception e) {
            Log.e("@AmFiles unzip", e.toString());
        } //catch
    } //unzip

    ///////////// FIM DESCOMPRESSORES /////////////


    ///////////// INICIO AUXILIARES /////////////
    private static boolean temAExtencaoCorreta(String pNomeDoFicheiro) {
        return pNomeDoFicheiro.toUpperCase().endsWith(EXTENCAO);
    } //temAExtencaoCorreta

    private static void criaDiretorio(String destino, String novoDiretorio) {
        File f = new File(destino + novoDiretorio);
        if (!f.isDirectory())
            f.mkdirs();
    } //criaDiretorio

    ///////////// FIM PESQUISA DE LIVROS E AFINS /////////////


} //AmFiles
