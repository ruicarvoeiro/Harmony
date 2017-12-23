package com.rossana.android.a261117ebookreader;

/**
 * Created by some bitch on 19/12/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MostrarListaActivity extends AppCompatActivity {
    //Constantes
    public final static String PAGINAS = "PAGINAS";
    public final static String NOME_DO_LIVRO = "NOME_DO_LIVRO";

    //Elementos do XML
    private ListView mLvLivros;

    //Adapter
    private MyLivroAdapter mLivroAdapter;

    //Outros Objetos
    private ArrayList<MyLivro> mLivros;
    private Intent mIntentQueMeChamou;
    private AmFiles mFiles;
    private AmUtil mUtil;

    //Handlers
    private AdapterView.OnItemClickListener mItemClickHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecao_ebook);
        init(savedInstanceState);
    } //onCreate


    void init(Bundle savedInstanceState) {
        //Initializar variaveis da classe
        mFiles = new AmFiles(this);
        mUtil = new AmUtil(this);

        mLivros = new ArrayList<MyLivro>();
        mIntentQueMeChamou = this.getIntent();
        recuperarDados(mIntentQueMeChamou);

        mLvLivros = (ListView) findViewById(R.id.idLvLivrosAApresentar);
        mLivroAdapter = new MyLivroAdapter(this, R.layout.livro_ll, mLivros);
        mLvLivros.setAdapter(mLivroAdapter);

        //Criacao dos handlers
        mItemClickHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyLivro l = mLivros.get(position);
                mUtil.utilFeedback("A abrir livro " + l.getTitulo());
                sendLivroASerLido(l);
            } //onItemClick
        }; //mItemClickHandler

        //Biding dos objetos aos handlers
        mLvLivros.setOnItemClickListener(mItemClickHandler);
        mLivroAdapter.notifyDataSetChanged();
    } //init


    private void sendLivroASerLido(MyLivro livro) {
        Intent intent = new Intent(this, LeituraActivity.class);
        intent.putExtra(NOME_DO_LIVRO, livro.getTitulo());
        intent.putExtra(PAGINAS, livro.getPaginas());
        startActivity(intent);
    } //sendListaDeLivros

    void recuperarDados(Intent pPacoteComOsDados) {
        if (pPacoteComOsDados != null) {
            ArrayList<File> livrosRecebidos = (ArrayList<File>)
                    pPacoteComOsDados.getSerializableExtra(MainActivity.ARRAY_LIVROS);

            if (livrosRecebidos != null) {
                mLivros.clear();
                for (File livro : livrosRecebidos)
                    mLivros.add(new MyLivro(this, livro));
            } //if
        } //if
    } //recuperarDados
} //MostrarListaActivity