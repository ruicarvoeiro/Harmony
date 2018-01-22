package com.rossana.android.a261117ebookreader;

import android.content.Intent;
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
    private TextView mTvNomeLivro;
    private WebView mWvZonaLeitura;
    private MenuItem mItemActionStar;
    private MenuItem mItemActionEmptyStar;
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
        mTvNomeLivro = (TextView) findViewById(R.id.idTvNomeLivro);
        mWvZonaLeitura = (WebView) findViewById(R.id.idWvZonaLeitura);
        mWvZonaLeitura.setVerticalScrollBarEnabled(true);
        mMusic = new AmSoundsFromLivro(this);
        mIntentQueMeChamou = this.getIntent();

        //Variaveis do menu
        mItemActionStar = (MenuItem) findViewById(R.id.idItemActionStar);
        mItemActionEmptyStar = (MenuItem) findViewById(R.id.idItemActionEmptyStar);
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
        return super.onCreateOptionsMenu(pMenu);
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.idItemActionEmptyStar) mudarEstrela();
        if (itemId == R.id.idItemActionStar) mudarEstrela();
        if (itemId == R.id.idItemActionSemVolume) mMusic.stopAllSounds();
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected


    private void mudarEstrela(){
        MenuItem pItemSelecionado = mMenu.getItem(1);
        AmFavoritosDB favoritos = new AmFavoritosDB(LeituraActivity.this);
        if (!(pItemSelecionado.getIcon() == ContextCompat.getDrawable(this, R.drawable.ic_action_empty_star))) {
            pItemSelecionado.setIcon(R.drawable.ic_action_empty_star);
            mMenu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_star));
            favoritos.inserirFavorito(mLivro.getISBN());
        } else{
            pItemSelecionado.setIcon(R.drawable.ic_action_empty_star);
            mMenu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_empty_star));
            favoritos.apagarFavorito(mLivro.getISBN());
        }
    } //mudarEstrela
    //////////////// END START MENU AJUDA////////////////

    @Override
    public void onPause() {
        super.onPause();
        mMusic.stopAllSounds();
    }
} //LeituraActivity