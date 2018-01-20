package com.rossana.android.a261117ebookreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GifSplashScreen extends AppCompatActivity {
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splah_screen_activity);
        mContext = this;

        //ImageView imageView = (ImageView) findViewById(R.id.idLogo);
        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        //Glide.with(this).load(R.raw.gif_image).into(imageViewTarget);
    }
}
