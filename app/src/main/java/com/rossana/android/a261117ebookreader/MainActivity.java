/*
cd c:\Android\SDK\platform-tools
adb shell
run-as com.rossana.android.a261117ebookreader
rm -r
*/
package com.rossana.android.a261117ebookreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Constantes da classe
    public final static String KEY_ARRAY_LIVROS = "KEY_ARRAY_LIVROS";

    //Objetos da Classe
    private Context mContext;

    //Objetos do XML
    private EditText mEtTiulo, mEtAutor;
    private Spinner mSpinner;
    private Button mBtnOkPesquisa, mBtnFavoritos;
    private ProgressBar mPBCarregarListaDeLivros;

    //Handlers
    private View.OnClickListener mClickHandler;

    //Outros Objetos
    private AmUtil mUtil;
    private String[] mListaDeGeneros;
    private AmFiles mFiles;
    private ArrayList<File> mLivrosFile;
    private Intent mIntentQueMeChamou;

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
        mPBCarregarListaDeLivros = (ProgressBar) findViewById(R.id.idPBCarregarListaDeLivros);
        mPBCarregarListaDeLivros.setVisibility(View.GONE);
        mIntentQueMeChamou = this.getIntent();
        mLivrosFile = getArrayListEnviado(mIntentQueMeChamou);

        mUtil = new AmUtil(this);
        mFiles = new AmFiles(this);
        mListaDeGeneros = getResources().getStringArray(R.array.nome_generos);
        mUtil.utilPopularSpinnerComOpcoes(mSpinner, mListaDeGeneros);

        mUtil.utilModernRequestPermission("android.permission.STORAGE", 1, false);
        mClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int iQuemFoiClicked = v.getId();
                mPBCarregarListaDeLivros.setVisibility(View.VISIBLE);

                if (iQuemFoiClicked == R.id.idBtnOkPesquisa) {
                    String tituloPedido = mEtTiulo.getText().toString();
                    String autorPedido = mEtAutor.toString();
                    String temaPedido = mListaDeGeneros[(mSpinner.getSelectedItemPosition())];
                    sendResultadoPesquisa(tituloPedido, autorPedido, temaPedido);
                } //if
                if (iQuemFoiClicked == R.id.idBtnFavoritos)
                    sendFavoritos();
            }//onItemClicked
        }; //mItemClickHandler

        Button[] buttonsRelevantes = {mBtnOkPesquisa, mBtnFavoritos};
        for (Button buttonDoMomento : buttonsRelevantes)
            buttonDoMomento.setOnClickListener(mClickHandler);
    } //init

    private ArrayList getArrayListEnviado(Intent dadosRecebidos) {
        ArrayList ret = new ArrayList();
        if (dadosRecebidos != null){
            ArrayList<File> livrosRecebidos = (ArrayList<File>) dadosRecebidos.getSerializableExtra(SplashScreen.KEY_ALL_LIVROS);
            ret = livrosRecebidos == null ? ret : livrosRecebidos;
        } //if
        return ret;
    } //getArrayListEnviado

    //TODO
    private void sendFavoritos() {
        AmFavoritosDB favoritos = new AmFavoritosDB(this);
        ArrayList listaASerEnviada = new ArrayList();
        Object[] valoresRecebidos = favoritos.getFavoritos().values().toArray();
        for (int i = 0; i < valoresRecebidos.length; i++) {
            MyLivro livro = new MyLivro();
            mFiles.preencherLivro((String) valoresRecebidos[i], livro);
            listaASerEnviada.add(livro);
        } //for

        sendListaDeLivros(listaASerEnviada);
    } //sendFavoritos

    //TODO
    private void sendResultadoPesquisa(String pStrTitulo, String pStrAutor, String itemSelecionado) {
        ArrayList<MyLivro> mLivros = new ArrayList<>();
        for (File livro : mLivrosFile) {
            MyLivro l = new MyLivro(livro);
            mFiles.getBasicData(l);
            if(livroNaoEstaNaLista(l, mLivros))
                mLivros.add(l);
        } //for

        sendListaDeLivros(mLivros);
    } //sendResultadoPesquisa

    private boolean livroNaoEstaNaLista(MyLivro l, ArrayList<MyLivro> mLivros) {
        for(MyLivro livroDaLista : mLivros)
            if(livroDaLista.equals(l))
                return false;
        return true;
    }

    private void sendListaDeLivros(ArrayList<MyLivro> pArrayLivros) {
        Intent intent = new Intent(this, MostrarListaActivity.class);
        intent.putExtra(KEY_ARRAY_LIVROS, pArrayLivros);
        startActivity(intent);
    } //sendListaDeLivros

    //////////////// START MENU RELATED ////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        MenuInflater helper = this.getMenuInflater();
        helper.inflate(R.menu.home_menu, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.idMenuItemHelpCenter) actionMenu(R.string.strAjuda);
        if (itemId==R.id.idMenuItemAboutUs) actionMenu(R.string.strAboutUs);
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    void actionMenu(int pString){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setMessage(getString(pString));
        mBuilder.show();
    }
    //////////////// END MENU RELATED ////////////////


    /* TODO LIST:
    * 1) Criar um ficheiro ou base de dados onde fiquem armazenados os favoritos
    * 2) Criar um ficheiro ou base de dados onde fiquem armazenados os recentes
    * 3) Criar um método getRecentes() que retorne um Arraylist de MyLivro, com os recentes
    * 4) Criar um método getFavoritos() que retorne um ArrayList de MyLivro, com os favoritos
    * 5) Usar o método pesquisaDeLivros da classe AmFiles
    * 6) Utilizar os métodos criados na MainActivity, de acordo ao necessário
    *
    * ?) Reavaliar o design do home_welcome? (Ver Material Design)
    * https://developer.android.com/training/material/get-started.html
    * */

} //HomeActivity