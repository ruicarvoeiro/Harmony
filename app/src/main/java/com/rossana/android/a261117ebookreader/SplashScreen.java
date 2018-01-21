package com.rossana.android.a261117ebookreader;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    private AmFiles mFiles;
    static final String KEY_ALL_LIVROS = "KEY_ALL_LIVROS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    } //onCreate
    void init(){
        mFiles = new AmFiles(this);
        final ArrayList<File> todosOsLivros = mFiles.getAllLivros();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra(KEY_ALL_LIVROS, todosOsLivros);
                startActivity(intent);
                finish();
            }
        },6500);
    }
}
