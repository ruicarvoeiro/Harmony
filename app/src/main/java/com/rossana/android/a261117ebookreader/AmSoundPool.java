package com.rossana.android.a261117ebookreader;

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
    private Context mContext;
    private AmSoundsFromLivro mSoundsFromLivro;
    private HashMap<Integer, Integer> mStreamsATocar;

    public AmSoundPool(ArrayList<String> listaDeFicheiros, float volume, Context context, AmSoundsFromLivro amSoundsFromLivro){
        this();
        mSoundsFromLivro = amSoundsFromLivro;
        mContext = context;
        carregarSons(listaDeFicheiros, context);
        this.volume = volume;
        mStreamsATocar = new HashMap();
    } //AmSoundPool

    public AmSoundPool(AmSoundsFromLivro amSoundsFromLivro){
        this();
        mSoundsFromLivro = amSoundsFromLivro;
    } //AmSoundPool

    public AmSoundPool(){
        volume = 1f;
    }


    public void carregarSons(final ArrayList<String> listaDeFicheiros, final Context context){
        listaDeSons = new HashMap<>();
        mSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 100);

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
                if(numeroDeSonsCarregados == listaDeFicheiros.size() && mSoundsFromLivro != null)
                    mSoundsFromLivro.musicasCarregadas();
            } //onLoadComplete
        }); //mSoundPool.setOnLoadCompleteListener
    } //carregarSons

    public void tocarSomEmLoop(String nome){
        int musicaATocar = listaDeSons.get(nome);
        int idDaStream = mSoundPool.play(musicaATocar, volume, volume, 1, -1, 1.0f);
        mStreamsATocar.put(musicaATocar, idDaStream);
    } //tocarSomEmLoop

    public void tocarSom(String nome){
        int musicaATocar = listaDeSons.get(nome);
        int idDaStream = mSoundPool.play(musicaATocar, volume, volume, 1, 0, 1.0f);
        mStreamsATocar.put(musicaATocar, idDaStream);
    } //tocarSom

    public void pararSom(String nome){
        int musicaAParar = listaDeSons.get(nome);
        if (mStreamsATocar.get(musicaAParar) != null)
                mSoundPool.stop(musicaAParar);
    } //pararSom

    public void pararTudo(){
        for (int musicaId : mStreamsATocar.values())
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
