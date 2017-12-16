package com.rossana.android.a261117ebookreader;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by some bitch on 14/12/2017.
 */

public class AmMusic {
    private Activity mActivity;
    public AmMusic(MainActivity activity) {
        this.mActivity = activity;
    }

    private void stopAllMusic() {
    }

    private void stopMusic(String nomeDoSom) {
    }

    private void startMusic(String nomeDoSom, boolean repete) {
        /*MediaPlayer mp;
        if(nomeDoSom == "DROPPLETS")
            mp = MediaPlayer.create(this.mActivity, R.raw.DROPPLETS);
        else if(nomeDoSom == "NATURE_AMBIENT")
            mp = MediaPlayer.create(this.mActivity, R.raw.NATURE_AMBIENT);
        else if(nomeDoSom == "RAIN")
            mp = MediaPlayer.create(this.mActivity, R.raw.RAIN);
        else //if(nomeDoSom == "THUNDER")
            mp = MediaPlayer.create(this.mActivity, R.raw.THUNDER);

        mp.setLooping(repete);
        mp.start();*/
    }

    public void startMusics(ArrayList<String> listaDeComandos) {
        for(String command : listaDeComandos)
            if(command.startsWith("START")){
                String[] comandoPartes = command.split(" ");
                String nomeDoSom = comandoPartes[1];
                boolean repete = comandoPartes[2] == "REPEAT";
                this.startMusic(nomeDoSom, repete);
            }
    } //startMusics

    public void stopMusics(ArrayList<String> listaDeComandos) {
        for(String command : listaDeComandos)
            if(command.startsWith("STOP")){
                String nomeDoSom = command.split(" ")[1];
                if(nomeDoSom == "ALL")
                    this.stopAllMusic();
                else
                    this.stopMusic(nomeDoSom);

            }
    } //stopMusics

}
