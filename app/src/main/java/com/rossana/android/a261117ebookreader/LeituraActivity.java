package com.rossana.android.a261117ebookreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rui_c on 13/12/2017.
 */

public class LeituraActivity extends Activity {
    //Objetos do XML
    private TextView mTvNomeLivro;
    private WebView mWvZonaLeitura;
    private ProgressBar mSpinnerDeProgresso;

    //Outros Objetos
    private Intent mIntentQueMeChamou;
    private ArrayList<String> mPaginas;
    private AmSoundsFromLivro mMusic;
    private String path;
    private int mNumeroDaPagina = 0;

    //Métodos
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_leitura);
        init(savedInstanceState);
    } //onCreate

    private void init(Bundle saveState) {
        //Initializar variaveis
        mTvNomeLivro = (TextView) findViewById(R.id.idTvNomeLivro);
        mWvZonaLeitura = (WebView) findViewById(R.id.idWvZonaLeitura);
        mSpinnerDeProgresso = (ProgressBar) findViewById(R.id.idPBCarregarLivro);
        mPaginas = new ArrayList<String>();
        mMusic = new AmSoundsFromLivro(this);
        mIntentQueMeChamou = this.getIntent();
        recuperarDados(mIntentQueMeChamou);
        mMusic.getMusicas(path);
    } //init

    public void displayPagina() {
        if(mNumeroDaPagina < 0)
            mNumeroDaPagina = 0;
        if (mNumeroDaPagina >= mPaginas.size())
            mNumeroDaPagina = mPaginas.size() - 1;
        mWvZonaLeitura.loadData(mPaginas.get(mNumeroDaPagina), "text/html", null);
        mMusic.playMusicas(mPaginas.get(mNumeroDaPagina));
    } //displayPagina

    private void recuperarDados(Intent pPacoteComOsDados) {
        if (pPacoteComOsDados != null) {
            ArrayList<String> paginasDoLivro = (ArrayList<String>)
                    pPacoteComOsDados.getSerializableExtra(MostrarListaActivity.PAGINAS);

            mPaginas = paginasDoLivro;
            String nomeDoLivro = pPacoteComOsDados.getStringExtra(MostrarListaActivity.NOME_DO_LIVRO);
            mTvNomeLivro.setText(nomeDoLivro);
            path = pPacoteComOsDados.getStringExtra(MostrarListaActivity.PATH_DO_LIVRO);
        } //if
    } //recuperarDados


    //O que é esta parte?
    ///////////////// START MENU AJUDA ////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        MenuInflater helper = this.getMenuInflater();
        helper.inflate(R.menu.leitura_menu, pMenu);
        return super.onCreateOptionsMenu(pMenu);

    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.idItemActionEmptyStar ||
                itemId == R.id.idItemActionStar) mudarEstrela();
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected

    public void mudarEstrela() {
        //TODO
    } //mudarEstrela

    //////////////// END START MENU AJUDA////////////////
} //LeituraActivity