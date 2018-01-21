package com.rossana.android.a261117ebookreader;

import android.app.Activity;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by some bitch on 04/01/2018.
 */

public class AmSoundsFromLivro {
    private Activity mContext;
    private AmSoundPool mSoundPool;
    private AmFiles mFiles;
    private LeituraActivity mLeitura;

    public AmSoundsFromLivro(Activity mContext) {
        this.mContext = mContext;
        this.mSoundPool = new AmSoundPool(this);
        this.mFiles = new AmFiles(mContext);
        this.mLeitura = (LeituraActivity) mContext;
    } //AmSoundsFromLivro

    public void playMusicas(String strHTMLaApresentar) {
        String regexParaInicioComentario = "(?:<!--)";
        String regexParaFimComentario = "(?:-->)";
        String regexParaConteudo = ".+";

        String regexParaEncontrarOsComentarios = regexParaInicioComentario + regexParaConteudo + regexParaFimComentario;
        Pattern padraoCompilado = Pattern.compile(regexParaEncontrarOsComentarios);
        Matcher matchesEncontradas = padraoCompilado.matcher(strHTMLaApresentar);

        for(int i = 0; matchesEncontradas.find(); i++){
            String comandoLimpo = matchesEncontradas.group();

            int contagemDeCarateres = strHTMLaApresentar.indexOf(comandoLimpo);
            strHTMLaApresentar.replace(comandoLimpo, "");

            comandoLimpo = comandoLimpo.replaceAll("<!--", "");
            comandoLimpo = comandoLimpo.replaceAll("-->", "");
            comandoLimpo.trim();

            int tempo = getTempo(contagemDeCarateres, strHTMLaApresentar);
            executarComando(comandoLimpo, tempo);
        } //while
    } //playMusicas

    public void getMusicas(String path){
        mSoundPool.carregarSons(mFiles.getFicheirosNoDiretorio(path + "/sounds"), mContext);
    }

    public void executarComando(String comando, int tempo) {
        //Existem 2 tipos de comandos:
        //1) Start
        //2) Stop
        // Os comandos START dividem-se em 2:
        // START NOME_DO_FICHEIRO NOREPEAT
        // START NOME_DO_FICHEIRO REPEAT

        //Os comandos STOP divide-se em 2:
        // STOP NOME_DO_FICHEIRO
        // STOP ALL

        String [] partesDoComando = comando.split(" ");
        String primeiroComando = partesDoComando[0];

        boolean eComandoStart = partesDoComando.length == 3 && primeiroComando.equals("START");
        boolean eComandoStop = partesDoComando.length == 2 && primeiroComando.equals("STOP");

        if(eComandoStart){
            String nomeDoFicheiro = partesDoComando[1];
            boolean repeat = partesDoComando[2].equalsIgnoreCase("REPEAT");

            if(repeat) {
                if (tempo == 0)
                    mSoundPool.tocarSomEmLoop(nomeDoFicheiro);
                else
                    mSoundPool.tocarSomDepoisDeMilissegundosEmLoop(nomeDoFicheiro, tempo);
            } //if

            else{
                if (tempo == 0)
                    mSoundPool.tocarSom(nomeDoFicheiro);
                else
                    mSoundPool.tocarSomDepoisDeMilissegundos(nomeDoFicheiro, tempo);
            } //else

        } //if

        else if(eComandoStop){
            String nomeDoFicheiro = partesDoComando[1];

            if(nomeDoFicheiro == "ALL")
                mSoundPool.pararTudo();
            else
                mSoundPool.pararSom(nomeDoFicheiro);
        } //else if
    } //executarComando

    public int getTempo(int carateresAtePosicao, String texto) {
        String textoAnalisar = texto.substring(0, carateresAtePosicao);
        int numeroDePalavras = textoAnalisar.split(" ").length - 1;
        return Math.round(numeroDePalavras *(250 / 60));
    } //getTempo

    public void musicasCarregadas(){
        View progressBar = mContext.findViewById(R.id.idPBCarregarLivro);
        View webView = mContext.findViewById(R.id.idWvZonaLeitura);
        if(progressBar != null)
            progressBar.setVisibility(View.GONE);

        if(webView != null)
            webView.setVisibility(View.VISIBLE);
        mLeitura.displayPagina();
    } //musicasCarregadas
} //AmSoundsFromLivro