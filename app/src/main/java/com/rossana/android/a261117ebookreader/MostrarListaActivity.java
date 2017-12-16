package com.rossana.android.a261117ebookreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MostrarListaActivity extends AppCompatActivity {
    //Elementos do XML
    private ListView mLvLivros;

    //Adapter
    private MyLivroAdapter mLivroAdapter;

    //Outros Objetos
    private ArrayList<Livro> mLivros;


    //Handlers
    private View.OnClickListener mClickHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_lista);
        init(savedInstanceState);
    } //onCreate


    void init(Bundle dadosRecebidos) {
        //Initializar variaveis da classe

        //Criacao dos handlers

        //Biding dos objetos aos handlers

    } //init

    /* TODO List:
    * 1) Ir buscar os dados enviados pela outra activity e colocalos na variavel correta
    * 2) Ligar o mLivroAdapter com o mLivros e o mLvLivros (não esquecer de usar livro_ll)
    * 3) Criar metodo que envie o livro selecionado para a LeituraActivity
    *
    * ?) Deveriamos poder excluir livros aqui?
    * */

} //MostrarListaActivity
