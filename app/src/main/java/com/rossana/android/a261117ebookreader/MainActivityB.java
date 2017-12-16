package com.rossana.android.a261117ebookreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivityB extends AppCompatActivity {
    //Objetos do XML
    private Button mBtnEscolherEbook;
    private WebView mWvVisualizacaoPagina;

    //Event Listeners
    View.OnClickListener mClickHandler;

    //Helper Classes
    AmUtil mUtil;
    AmFiles mFile;
    AmMusic mMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    } //onCreate

    private void init(){
        //Initializar variaveis da classe
        mBtnEscolherEbook = (Button) findViewById(R.id.idBtnEscolherEbook);
        mWvVisualizacaoPagina = (WebView) findViewById(R.id.idWvMyBrowser);

        mUtil = new AmUtil(this);
        mFile = new AmFiles();
        //mMusic = new AmMusic(this);

        //preparar o listener
        mClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnEscolherEbook.getId() == v.getId())
                    //mUtil.selectAFile();
                    showMeSomething();

            } //onClick
        }; //mBtnEscolherEbook

        //adicionar o Listener às variáveis
        mBtnEscolherEbook.setOnClickListener(mClickHandler);
    }


    public static ArrayList<HashMap<String, Integer>> showMeSomething() {
        String strHTMLaApresentar = "<!--START NATURE_AMBIENT NOREPEAT-->\n" +
                "<h1>The Buckwheat</h1>\n" +
                "<p>VERY often, after a violent thunder-storm, a field of buckwheat appears blackened and singed, as if a flame of fire had passed over it. The country people say that this appearance is caused by lightning; but I will tell you what the sparrow says, and the sparrow heard it from an old willow-tree which grew near a field of buckwheat, and is there still. It is a large venerable tree, though a little crippled by age. The trunk has been split, and out of the crevice grass and brambles grow. The tree bends forward slightly, and the branches hang quite down to the ground just like green hair. Corn grows in the surrounding fields, not only rye and barley, but oats,-pretty oats that, when ripe, look like a number of little golden canary-birds sitting on a bough. The corn has a smiling look and the heaviest and richest ears bend their heads low as if in pious humility. Once there was also a field of buckwheat, and this field was exactly opposite to old willow-tree. The buckwheat did not bend like the other grain, but erected its head proudly and stiffly on the stem.</p>\n" +
                "<p>\"I am as valuable as any other corn,\" said he, \"and I am much handsomer; my flowers are as beautiful as the bloom of the apple blossom, and it is a pleasure to look at us. Do you know of anything prettier than we are, you old willow-tree?\"</p>";
        ArrayList<HashMap<String, Integer>> listaDeComandosETempos = getMusicasListaAndTempo(strHTMLaApresentar);
        return listaDeComandosETempos;
    }


    private static  ArrayList<HashMap<String, Integer>> getMusicasListaAndTempo(String strHTMLaApresentar) {
        ArrayList<HashMap<String, Integer>> ret = new ArrayList<HashMap<String, Integer>>();
        String regexParaInicioComentario = "(<!--)";
        String regexParaFimComentario = "(-->)";
        String regexParaConteudo = ".+";

        String regexParaEncontrarOsComentarios = regexParaInicioComentario + regexParaConteudo + regexParaFimComentario;
        Pattern padraoCompilado = Pattern.compile(regexParaEncontrarOsComentarios);
        Matcher matchesEncontradas = padraoCompilado.matcher(strHTMLaApresentar);

        for(int i = 0; matchesEncontradas.find(); i++){
            HashMap<String, Integer> comandoECarateresAteComando = new HashMap<String, Integer>();
            String comandoLimpo = matchesEncontradas.group(i);
            int contagemDeCarateres = getCarateresELimpa(comandoLimpo, strHTMLaApresentar);

            comandoLimpo = comandoLimpo.replaceAll("<!--", "");
            comandoLimpo = comandoLimpo.replaceAll("-->", "");
            comandoLimpo.trim();
            System.out.println(comandoLimpo.contains("<!--"));
            comandoECarateresAteComando.put(comandoLimpo, contagemDeCarateres);
            ret.add(comandoECarateresAteComando);
        } //while
        return ret;
    } //getMusicasListaAndTempo


    private static int getCarateresELimpa(String comandoLimpo, String strHTMLaApresentar) {
        int characteres = strHTMLaApresentar.indexOf(comandoLimpo);
        strHTMLaApresentar.replace(comandoLimpo, "");
        return characteres;
    }

} //MainActivity