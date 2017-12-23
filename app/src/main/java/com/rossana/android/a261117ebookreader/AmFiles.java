package com.rossana.android.a261117ebookreader;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by some bitch on 14/12/2017.
 */

public class AmFiles {

    public AmFiles(Activity a){
        this.mContext = a;
    }

    //Constantes
    public static final String EXTENCAO = ".MACOAS";

    private Context mContext;

    ///////////// INICIO PESQUISA DE LIVROS E AFINS /////////////
    public ArrayList<MyLivro> pesquisaDeLivros(String pStrTitulo, String pStrAutor, Object pitemSelecionado) {
        ArrayList<MyLivro> livrosQueCoincidemComAPesquisa = new ArrayList<MyLivro>();
        ArrayList<File> todosOsLivrosExistentes = getAllLivros();
        return livrosQueCoincidemComAPesquisa;
    } //pesquisaDeLivros

    public void getAllLivrosInDirectory(String nomeDoDiretorio, ArrayList<File> listaDeLivros) {
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

    public ArrayList<File> getAllLivros() {
        String diretorio = Environment.getExternalStoragePublicDirectory("").getPath();

        //TODO: procurar tanto no sdCard, como na memória interna
        //Atualmente só procuro na memória interna

        ArrayList<File> ficheirosQueSaoLivros = new ArrayList<File>();
        getAllLivrosInDirectory(diretorio, ficheirosQueSaoLivros);

        return ficheirosQueSaoLivros;
    } //getAllLivros
    ///////////// FIM PESQUISA DE LIVROS E AFINS /////////////


    ///////////// INICIO DESCOMPRESSORES /////////////
    public boolean unzip(MyLivro livro, String fullPathDoLivro) {
        try {
            FileInputStream fin = new FileInputStream(fullPathDoLivro);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze;

            int numeroDaPagina = 0;

            while ((ze = zin.getNextEntry()) != null) {
                String nomeDoFicheiroOuDiretorio = ze.getName();

                if (ze.isDirectory()){
                    File novaPasta = new File(mContext.getFilesDir(), nomeDoFicheiroOuDiretorio);
                    novaPasta.mkdirs();
                } //if

                else {
                    OutputStream fout = new FileOutputStream(new File(mContext.getFilesDir(), ze.getName()), true);

                    byte[] buffer = new byte[2048];
                    BufferedOutputStream bos = new BufferedOutputStream(fout, buffer.length);

                    int valor;
                    String texto = "";
                    //while ((valor = zin.read(buffer, 0, buffer.length)) != -1) {
                    if(ze.getName().endsWith(".html") || ze.getName().endsWith("META_INFO")) {
                        while ((valor = zin.read(buffer)) != -1) {
                            bos.write(buffer, 0, valor);
                            texto += new String(buffer, "UTF-8");
                            buffer = new byte[2048];
                        }
                    }
                    else
                        while ((valor = zin.read(buffer)) != -1)
                            bos.write(buffer, 0, valor);



                    if(ze.getName().endsWith(".html"))
                        livro.addPagina(texto, numeroDaPagina++);

                    if(ze.getName().endsWith("META_INFO"))
                        livro.addMetadata(texto);

                    bos.flush();
                    bos.close();

                    fout.flush();
                    fout.close();
                    zin.closeEntry();
                } //else
            } //while

            zin.close();
            return true;
        } catch (Exception e) {
            Log.e("@AmFiles unzip", e.toString());
        } //catch
        return false;
    } //unzip

    ///////////// FIM DESCOMPRESSORES /////////////


    ///////////// INICIO LEITURA DE FICHEIROS /////////////
    public String getTextoFromFicheiro(String pathDoFicheiro) {
        String strRet = "";
        try {
            FileInputStream fr = new FileInputStream(new File(mContext.getFilesDir(), pathDoFicheiro));
            InputStreamReader isr = new InputStreamReader(fr, "UTF-8");

            if(isr != null){
                int i;
                while((i = isr.read()) != -1)
                    strRet += (char) i;
            }

            fr.close();
            isr.close();
        }catch(Exception e){
            Log.e("HELP", e.toString());
        }

        return strRet;
    } //getTextoFromFicheiro

    ///////////// FIM LEITURA DE FICHEIROS /////////////


    ///////////// INICIO AUXILIARES /////////////
    public static boolean temAExtencaoCorreta(String pNomeDoFicheiro) {
        return pNomeDoFicheiro.toUpperCase().endsWith(EXTENCAO);
    } //temAExtencaoCorreta

    public  static boolean ficheiroJaExiste(String path) {
        File file = new File(path);
        return file.exists();
    } //verificaSeOFicheiroJaExiste

    public boolean pastaJaExiste(String nomeDoDiretorio) {
        File novaPasta = new File(mContext.getFilesDir(), nomeDoDiretorio);
        return novaPasta.exists();
    } //pastaJaExiste

    ///////////// FIM AUXILIARES /////////////

} //AmFiles