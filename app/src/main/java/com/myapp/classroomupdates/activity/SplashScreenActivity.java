package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.myapp.classroomupdates.R;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler;
    private TextView classUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        classUpdate= findViewById(R.id.tv_class_updates);

        Typeface typeface= Typeface.createFromAsset(getAssets(), "fonts/my_font.otf");
        classUpdate.setTypeface(typeface);

        handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, BeforeLoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
