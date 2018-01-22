package com.rossana.android.a261117ebookreader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    private AmUtil mUtil;
    private AmFiles mFiles;
    static final String KEY_ALL_LIVROS = "KEY_ALL_LIVROS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mUtil = new AmUtil(this);
        //<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
        //<uses-permission android:name="android.permission.STORAGE" />
        String[] precisoDisto = {
                "android.permission.READ_EXTERNAL_STORAGE",
                //"android.permission.STORAGE"
        };
        int res = checkCallingOrSelfPermission(precisoDisto[0]);
        if (res == PackageManager.PERMISSION_GRANTED)
            init();
        else
            mUtil.utilModernRequestPermission(precisoDisto[0], 123, true);
    } //onCreate

    void init() {
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
        }, 2000);
    } //init

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        boolean bTenhoTodasAsPermissoes = true;
        for (int i = 0; i < grantResults.length; i++)
            bTenhoTodasAsPermissoes = bTenhoTodasAsPermissoes && grantResults[i] == PackageManager.PERMISSION_GRANTED;
        if(bTenhoTodasAsPermissoes)
            init();
        else
            finish();
    } //onRequestPermissionsResult
}