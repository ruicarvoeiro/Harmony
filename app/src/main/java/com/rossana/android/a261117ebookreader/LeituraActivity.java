package com.rossana.android.a261117ebookreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rui_c on 13/12/2017.
 */

public class LeituraActivity extends AppCompatActivity {
    //Objetos do XML
    private TextView mTvNomeLivro;
    private WebView mWvZonaLeitura;
    private MenuItem mItemActionStar;
    private MenuItem mItemActionEmptyStar;
    private MenuItem mItemActionMarcador;
    private MenuItem mItemActionGoHome;
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
        mWvZonaLeitura.setVerticalScrollBarEnabled(true);
        mPaginas = new ArrayList<String>();
        mMusic = new AmSoundsFromLivro(this);

        //Variaveis do menu
        mItemActionStar = (MenuItem) findViewById(R.id.idItemActionStar);
        mItemActionEmptyStar = (MenuItem) findViewById(R.id.idItemActionEmptyStar);
        mItemActionMarcador = (MenuItem) findViewById(R.id.idItemActionMarcador);
        mItemActionGoHome = (MenuItem) findViewById(R.id.idItemActionGoHome);
        swipe();

        mIntentQueMeChamou = this.getIntent();

        recuperarDados(mIntentQueMeChamou);

        mMusic.getMusicas(path);
    } //init

    private void swipe(){
        mWvZonaLeitura.setOnTouchListener(new AmOnSwipeTouchListener(LeituraActivity.this) {
            public void onSwipeRight() {
                mNumeroDaPagina--;
                if(mNumeroDaPagina < 0)
                    mNumeroDaPagina = 0;
                mMusic.stopAllSounds();
                displayPagina();
            }
            public void onSwipeLeft() {
                mNumeroDaPagina++;
                if (mNumeroDaPagina >= mPaginas.size())
                    mNumeroDaPagina = mPaginas.size() - 1;
                displayPagina();
            }
        });
        mWvZonaLeitura.setVerticalScrollBarEnabled(true);
        //pára o swipe e faz com que funcione o coiso para ir para baixo
        //mWvZonaLeitura.setOnTouchListener(null);
    } //swipe

    public void displayPagina() {
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


    ///////////////// START MENU AJUDA ////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        MenuInflater helper = this.getMenuInflater();
        helper.inflate(R.menu.leitura_menu, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.idItemActionEmptyStar) mudarEstrela(mItemActionEmptyStar);
        if (itemId == R.id.idItemActionStar) mudarEstrela(mItemActionStar);
        if (itemId == R.id.idItemActionGoHome) goBackToHome();
        if (itemId == R.id.idItemActionSemVolume) soundOff();
        if (itemId == R.id.idItemActionMarcador) marcador();
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    private void marcador() {
        //highlight, css?
        //TODO
    }

    private void soundOff() {
        //TODO
        //mMusic.setSoundEffectsEnabled(false);
    }

    private void mudarEstrela(MenuItem pItemSelecionado){
        if (pItemSelecionado == mItemActionStar){
            pItemSelecionado.setIcon(R.drawable.ic_action_empty_star);
            //E se foress para o caralhinho?
        }
        if (pItemSelecionado == mItemActionEmptyStar){
            pItemSelecionado.setIcon(R.drawable.ic_action_star);
        }
    }
    private void goBackToHome(){
        Intent voltaParaHome = new Intent(LeituraActivity.this, MainActivity.class);
        startActivity(voltaParaHome);
    }
    //////////////// END START MENU AJUDA////////////////

    @Override
    public void onPause() {
        super.onPause();
        mMusic.stopAllSounds();
    }
} //LeituraActivity