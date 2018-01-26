package com.rossana.android.a261117ebookreader;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by rui_c on 13/12/2017.
 */

public class LeituraActivity extends AppCompatActivity {
    //Objetos do XML
    private WebView mWvZonaLeitura;
    //Outros Objetos
    private Intent mIntentQueMeChamou;
    private MyLivro mLivro;
    private AmSoundsFromLivro mMusic;
    private int mNumeroDaPagina = 0;
    private Menu mMenu;

    //Métodos
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_leitura);
        init(savedInstanceState);
    } //onCreate

    private void init(Bundle saveState) {
        //Initializar variaveis
        mWvZonaLeitura = (WebView) findViewById(R.id.idWvZonaLeitura);
        mWvZonaLeitura.setVerticalScrollBarEnabled(true);
        mMusic = new AmSoundsFromLivro(this);
        mIntentQueMeChamou = this.getIntent();

        swipe();

        mLivro = recuperarDados(mIntentQueMeChamou);

        mMusic.getMusicas(mLivro.getISBN());
    } //init

    private void swipe(){
        mWvZonaLeitura.setOnTouchListener(new AmOnSwipeTouchListener(LeituraActivity.this) {
            public void onSwipeRight() {
                mNumeroDaPagina--;
                if(mNumeroDaPagina < 0)
                    mNumeroDaPagina = 0;
                else {
                    mMusic.stopAllSounds();
                    displayPagina();
                }
            }
            public void onSwipeLeft() {
                mNumeroDaPagina++;
                if (mNumeroDaPagina >= mLivro.getPaginas().size())
                    mNumeroDaPagina = mLivro.getPaginas().size() - 1;
                else displayPagina();
            }
        });
        mWvZonaLeitura.setScrollContainer(true);
    } //swipe

    public void displayPagina() {
        String textoASerCarregado = mLivro.getPaginas().get(mNumeroDaPagina);
        mWvZonaLeitura.loadData(textoASerCarregado, "text/html", null);
        mMusic.playMusicas(textoASerCarregado);
    } //displayPagina

    private MyLivro recuperarDados(Intent pPacoteComOsDados) {
        MyLivro livro = null;
        if (pPacoteComOsDados != null) {
            livro = (MyLivro)
                    pPacoteComOsDados.getSerializableExtra(MostrarListaActivity.KEY_LIVRO);
        } //if
        return livro;
    } //recuperarDados


    ///////////////// START MENU AJUDA ////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        mMenu = pMenu;
        MenuInflater helper = this.getMenuInflater();
        helper.inflate(R.menu.leitura_menu, pMenu);
        validar();
        return super.onCreateOptionsMenu(pMenu);
    }//onCreateOptionsMenu
    void validar(){
        AmFavoritosDB favoritos = new AmFavoritosDB(LeituraActivity.this);
        boolean valido = favoritos.validarFavoritos(mLivro.getISBN());
        if(valido) {
            mMenu.getItem(2).setVisible(false);
            mMenu.getItem(3).setVisible(true);
        }
        if(!valido){
            mMenu.getItem(2).setVisible(true);
            mMenu.getItem(3).setVisible(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.idItemActionEmptyStar) mudarEstrela(itemId);
        if (itemId == R.id.idItemActionStar) mudarEstrela(itemId);
        if (itemId == R.id.idItemActionSemVolume) musicaATocarOuNao(itemId);
        if (itemId == R.id.idItemActionComVolume) musicaATocarOuNao(itemId);
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    private void musicaATocarOuNao(int itemID){
        int somOff = mMenu.getItem(0).getItemId();
        int somOn = mMenu.getItem(1).getItemId();
        String textoASerCarregado = mLivro.getPaginas().get(mNumeroDaPagina);
        if(itemID == somOff){
            mMenu.getItem(1).setVisible(true);
            mMusic.stopAllSounds();
            mMenu.getItem(0).setVisible(false);
        }
        if(itemID == somOn){
            mMenu.getItem(0).setVisible(true);
            mMusic.playMusicas(textoASerCarregado);
            mMenu.getItem(1).setVisible(false);
        }
    }
    private void mudarEstrela(int itemID){
        int estrelaVazia = mMenu.getItem(2).getItemId();
        int estrelaCheia = mMenu.getItem(3).getItemId();

        AmFavoritosDB favoritos = new AmFavoritosDB(LeituraActivity.this);
        if (itemID == estrelaVazia) {
            mMenu.getItem(3).setVisible(true);
            favoritos.inserirFavorito(mLivro.getISBN());
            mMenu.getItem(2).setVisible(false);
        }
        if (itemID == estrelaCheia) {
            mMenu.getItem(2).setVisible(true);
            favoritos.apagarFavorito(mLivro.getISBN());
            mMenu.getItem(3).setVisible(false);
        }
    } //mudarEstrela
    //////////////// END START MENU AJUDA////////////////

    @Override
    public void onPause() {
        super.onPause();
        mMusic.stopAllSounds();
    }
} //LeituraActivity