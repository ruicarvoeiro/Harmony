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
    public void getAllLivrosInDirectory(String nomeDoDiretorio, ArrayList<File> listaDeLivros) {
        File directory = new File(nomeDoDiretorio);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList)
                if (file.isFile()) {
                    if (temAExtencaoCorreta(file.getName())  && !arrayListContemLivro(listaDeLivros, file))
                        listaDeLivros.add(file);
                } else if (file.isDirectory())
                    getAllLivrosInDirectory(file.getAbsolutePath(), listaDeLivros);
    } //getAllLivrosInDirectory

    private boolean arrayListContemLivro(ArrayList<File> fList, File file) {
        for (File fListFile : fList)
            if(fListFile.equals(file))
                return true;
        return false;
    }

    public ArrayList<File> getAllLivros() {
        String diretorio = Environment.getExternalStoragePublicDirectory("").getPath();

        ArrayList<File> ficheirosQueSaoLivros = new ArrayList<File>();
        getAllLivrosInDirectory(diretorio, ficheirosQueSaoLivros);

        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList)
            if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
            //if(file.isDirectory() && file.canRead())
                getAllLivrosInDirectory(file.getAbsolutePath(), ficheirosQueSaoLivros);

        return ficheirosQueSaoLivros;
    } //getAllLivros
    ///////////// FIM PESQUISA DE LIVROS E AFINS /////////////


    ///////////// INICIO DESCOMPRESSORES /////////////
    public boolean unzip(MyLivro livro) {
        try {
            FileInputStream fin = new FileInputStream(livro.getFile());
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

    public boolean getMetadata(MyLivro livro, String fullPathDoLivro) {
        try {
            FileInputStream fin = new FileInputStream(fullPathDoLivro);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze;

            while ((ze = zin.getNextEntry()) != null) {
                if (!ze.isDirectory() && ze.getName().endsWith("META_INFO")) {
                    byte[] buffer = new byte[2048];
                    int valor;
                    String texto = "";
                    while ((valor = zin.read(buffer)) != -1) {
                        texto += new String(buffer, "UTF-8");
                        buffer = new byte[2048];
                    }
                    livro.addMetadata(texto);
                    zin.closeEntry();
                    return true;
                } //if
            } //while
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

    public static boolean ficheiroJaExiste(String path) {
        File file = new File(path);
        return file.exists();
    } //verificaSeOFicheiroJaExiste

    public boolean pastaJaExiste(String nomeDoDiretorio) {
        File novaPasta = new File(mContext.getFilesDir(), nomeDoDiretorio);
        return novaPasta.exists();
    } //pastaJaExiste

    public static String getNomeFicheiro(String path){
        String [] partes = path.split("/");
        String nomeFicheiro = partes[partes.length - 1];
        String[] nomeEExtencao = nomeFicheiro.split("\\.");
        return nomeEExtencao[0];
    } //getNomeFicheiro


    public ArrayList getFicheirosNoDiretorio(String path){
        File diretorio = new File(mContext.getFilesDir(), path);

        ArrayList<String> listaDeFicheiros = new ArrayList<>();
        if (diretorio.isDirectory())
            for (File ficheiro : diretorio.listFiles())
                listaDeFicheiros.add(ficheiro.getPath());
        return listaDeFicheiros;
    } //getFicheirosNoDiretorio
    ///////////// FIM AUXILIARES /////////////


    ///////////// PREENCHER LIVRO /////////////

    public void getConteudos(MyLivro mLivro) {
        try {
            String path = mLivro.getISBN();
            FileInputStream fin = new FileInputStream(mLivro.getFile());
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = zin.getNextEntry();

            String nomeDoDiretorio = ze.getName().split("/")[0];
            zin.close();
            fin.close();

            if (pastaJaExiste(nomeDoDiretorio))
                preencherLivro(nomeDoDiretorio, mLivro);
            else
                unzip(mLivro);
        }catch (Exception e){
            e.toString();
        } //catch
    } //getConteudos

    private void preencherLivro(String nomeDoDiretorio, MyLivro mLivro) {
        String metadata = getTextoFromFicheiro(nomeDoDiretorio + "/META_INFO");
        mLivro.addMetadata(metadata);

        File diretorioComOsFicheiros = new File(mContext.getFilesDir(), nomeDoDiretorio + "/1/");
        for(File ficheiro : diretorioComOsFicheiros.listFiles())
            mLivro.addPagina(getTextoFromFicheiro(nomeDoDiretorio + "/1/" + ficheiro.getName()),
                    mLivro.getPaginas().size());
    } //preencherLivro

    public void getBasicData(MyLivro mLivro){
        String path = mLivro.getFile().getPath();
        try {
            FileInputStream fin = new FileInputStream(path);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = zin.getNextEntry();

            String nomeDoDiretorio = ze.getName().split("/")[0];
            zin.close();
            fin.close();

            if (pastaJaExiste(nomeDoDiretorio))
                preencherLivro(nomeDoDiretorio, mLivro);
            else
                getMetadata(mLivro, path);
        }catch (Exception e){
            e.toString();
        } //catch
    } //getBasicData

    ///////////// FIM PREENCHER LIVRO /////////////

} //AmFiles