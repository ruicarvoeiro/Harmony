package com.rossana.android.a261117ebookreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by some bitch on 04/01/2018.
 */

public class AmSoundPool {
    private static SoundPool mSoundPool;
    private HashMap<String, Integer> listaDeSons;
    private float volume; //0 a 1
    private int numeroDeSonsCarregados = 0;
    private ProgressDialog progress;
    private Context mContext;

    public AmSoundPool(ArrayList<String> listaDeFicheiros, float volume, Context context){
        mContext = context;
        carregarSons(listaDeFicheiros, context);
        this.volume = volume;
    } //AmSoundPool

    public AmSoundPool(){
        volume = 0.5f;
    }


    public void carregarSons(final ArrayList<String> listaDeFicheiros, final Context context){
        listaDeSons = new HashMap<>();
        mSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 100);
        progress = new ProgressDialog(context);
        progress.setMessage("Loading awesomeness");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        for(String path : listaDeFicheiros) {
            String nome = AmFiles.getNomeFicheiro(path);
            int id = mSoundPool.load(path, 0);
            listaDeSons.put(nome, id);
        } //for

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                numeroDeSonsCarregados++;
                int progresso = numeroDeSonsCarregados / listaDeFicheiros.size() * 100;
                if(numeroDeSonsCarregados == listaDeFicheiros.size())
                    progress.dismiss();
            } //onLoadComplete
        }); //mSoundPool.setOnLoadCompleteListener
    } //carregarSons

    public void tocarSomEmLoop(String nome){
        int musicaATocar = listaDeSons.get(nome);
        mSoundPool.play(musicaATocar, volume, volume, 0, -1, 1.0f);
    } //tocarSomEmLoop

    public void tocarSom(String nome){
        int musicaATocar = listaDeSons.get(nome);

        mSoundPool.play(musicaATocar, volume, volume, 0, 0, 1.0f);
    } //tocarSom

    public void pararSom(String nome){
        int musicaAParar = listaDeSons.get(nome);
        mSoundPool.stop(musicaAParar);
    } //pararSom

    public void pararTudo(){
        for (int musicaId : listaDeSons.values())
            mSoundPool.stop(musicaId);
    } //pararTudo

    public void end(){
        mSoundPool.release();
        mSoundPool = null;
    } //end

    public void tocarSomDepoisDeMilissegundos(final String nome, long milissegundos){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tocarSom(nome);
            } //run
        }, milissegundos);
    } //tocarSomDepoisDeMilissegundos

    public void tocarSomDepoisDeMilissegundosEmLoop(final String nome, long milissegundos){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tocarSomEmLoop(nome);
            } //run
        }, milissegundos);
    } //tocarSomDepoisDeMilissegundosEmLoop


} //AmSoundPool
