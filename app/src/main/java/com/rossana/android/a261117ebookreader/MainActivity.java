package com.rossana.android.a261117ebookreader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Constantes da classe
    public final static String ARRAY_LIVROS = "ARRAY_LIVROS";

    //Objetos da Classe
    Context mContext;

    //Objetos do XML
    private EditText mEtTiulo, mEtAutor;
    private Spinner mSpinner;
    private Button mBtnOkPesquisa, mBtnFavoritos, mBtnRecentes;

    //Handlers
    private View.OnClickListener mClickHandler;

    //Outros Objetos
    private AmUtil mUtil;
    private String[] mListaDeGeneros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_welcome);
        init(savedInstanceState);
    } //onCreate

    void init(Bundle dadosRecebidos) {
        //Initializar variaveis da classe
        mContext = this;
        mEtTiulo = (EditText) findViewById(R.id.idEtTitulo);
        mEtAutor = (EditText) findViewById(R.id.idEtAutor);
        mSpinner = (Spinner) findViewById(R.id.idSGeneros);
        mBtnOkPesquisa = (Button) findViewById(R.id.idBtnOkPesquisa);
        mBtnFavoritos = (Button) findViewById(R.id.idBtnFavoritos);
        mBtnRecentes = (Button) findViewById(R.id.idBtnRecentes);

        mUtil = new AmUtil(this);
        mListaDeGeneros = getResources().getStringArray(R.array.nome_generos);
        mUtil.utilPopularSpinnerComOpcoes(mSpinner, mListaDeGeneros);

        mClickHandler = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int iQuemFoiClicked = v.getId();
                if (iQuemFoiClicked == R.id.idBtnOkPesquisa)
                    sendResultadoPesquisa(mEtTiulo.getText().toString(), mEtAutor.toString(), mSpinner.getSelectedItem());
                if (iQuemFoiClicked == R.id.idBtnFavoritos)
                    sendFavoritos();
                if (iQuemFoiClicked == R.id.idBtnRecentes)
                    sendRecentes();
            }//onClick
        }; //mClickHandler
        Button[] buttonsRelevantes = {mBtnOkPesquisa, mBtnFavoritos, mBtnRecentes};
        for (Button buttonDoMomento : buttonsRelevantes)
            buttonDoMomento.setOnClickListener(mClickHandler);
    } //init

    private void sendRecentes() {
        sendListaDeLivros(null);
    } //sendRecentes

    private void sendFavoritos() {
        sendListaDeLivros(null);
    } //sendFavoritos

    private void sendResultadoPesquisa(String pStrTitulo, String pStrAutor, Object itemSelecionado) {
        sendListaDeLivros(null);
    } //sendResultadoPesquisa

    private void sendListaDeLivros(ArrayList pArrayLivros){
        Intent intent = new Intent(this, MostrarListaActivity.class);
        intent.putExtra(ARRAY_LIVROS, pArrayLivros);
        startActivity(intent);
    } //sendListaDeLivros

    /* TODO LIST:
    * 1) Criar um ficheiro ou base de dados onde fiquem armazenados os favoritos
    * 2) Criar um ficheiro ou base de dados onde fiquem armazenados os recentes
    * 3) Criar um método getRecentes() que retorne um Arraylist de Livro, com os recentes
    * 4) Criar um método getFavoritos() que retorne um ArrayList de Livro, com os favoritos
    * 5) Usar o método pesquisaDeLivros da classe AmFiles
    * 6) Utilizar os métodos criados na MainActivity, de acordo ao necessário
    *
    * ?) Reavaliar o design do home_welcome? (Ver Material Design)
    * https://developer.android.com/training/material/get-started.html
    * */

} //HomeActivity
