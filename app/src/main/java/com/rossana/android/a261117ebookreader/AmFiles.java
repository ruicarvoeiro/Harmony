package com.rossana.android.a261117ebookreader;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by some bitch on 14/12/2017.
 */

public class AmFiles {
    private static final String EXTENCAO = ".PNG";

    public static ArrayList<File> getAllLivros() {
        String diretorio = "Não sei";
        // TODO: Descobrir como vamos ao diretorio raíz do dispositivo e do sd card

        //Environment.getDataDirectory().getAbsolutePath()

        ArrayList<File> files = filesAtDir(diretorio);
        return files;
    } //getAllLivros

    //TODO: Código não testado, verificar se funciona ou não
    public static ArrayList<File> filesAtDir(String pPathAtual){
        ArrayList<File> livrosEncontrados = new ArrayList<File>();
        File ficheiroOuPastaDoDiretorio = new File(pPathAtual);
        if (ficheiroOuPastaDoDiretorio != null) {
            if (ficheiroOuPastaDoDiretorio.isDirectory()) {
                File[] ficheirosDentroDoDiretorio = ficheiroOuPastaDoDiretorio.listFiles();

                for (File ficheiro : ficheirosDentroDoDiretorio) {
                    if (ficheiro.isFile()) {
                        if(temAExtencaoCorreta(ficheiro.getName()))
                            livrosEncontrados.add(ficheiro);
                    } //if

                    else if (ficheiro.isFile()) {
                        if(temAExtencaoCorreta(ficheiro.getName()))
                            livrosEncontrados.addAll(filesAtDir(ficheiro.getAbsolutePath()));
                    } //else if
                } //for
            } //if
        }
        return livrosEncontrados;
    } //filesAtDir

    private static boolean temAExtencaoCorreta(String pNomeDoFicheiro){
        String extencaoFicheiro = pNomeDoFicheiro.substring(
                pNomeDoFicheiro.length() - EXTENCAO.length()
        );
        return EXTENCAO.compareToIgnoreCase(extencaoFicheiro) == 0;
    } //temAExtencaoCorreta

    //TODO: Este método só poderá começar a ser implementado depois de eu terminar a minha parte (sorry)
    public static ArrayList<Livro> pesquisaDeLivros(){
        ArrayList<Livro> livrosQueCoincidemComAPesquisa = new ArrayList<Livro>();
        ArrayList<File> todosOsLivrosExistentes = getAllLivros();
        return livrosQueCoincidemComAPesquisa;
    } //pesquisaDeLivros


    public static Livro FileToLivro(File livro){
        return null;
    } //FileToLivro

} //AmFiles