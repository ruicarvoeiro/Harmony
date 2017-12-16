package com.rossana.android.a261117ebookreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by rui_c on 13/12/2017.
 */

public class LeituraActivity extends Activity {
    //Objetos do XML
    private TextView mTvNomeLivro;
    private WebView mWvZonaLeitura;

    //Métodos
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_leitura);

        init(savedInstanceState);
    } //onCreate

    void init(Bundle saveState){
        //Initializar variaveis
        mTvNomeLivro = (TextView) findViewById(R.id.idTvNomeLivro);
        mWvZonaLeitura = (WebView) findViewById(R.id.idWvMyBrowser);

    } //init

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

    void mudarEstrela(){
        //TODO
    } //mudarEstrela

    //////////////// END START MENU AJUDA////////////////
} //LeituraActivity
