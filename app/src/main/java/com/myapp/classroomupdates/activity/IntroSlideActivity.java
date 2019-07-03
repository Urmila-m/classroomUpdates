package com.myapp.classroomupdates.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.adapter.IntroSliderAdapter;

import static com.myapp.classroomupdates.Globals.firstInstall;

public class IntroSlideActivity extends PreferenceInitializingActivity {

    private ViewPager viewPager;
    private IntroSliderAdapter adapter;
    private TabLayout tabLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slide);

        viewPager= findViewById(R.id.vp_intro);
        tabLayout= findViewById(R.id.tl_intro);
        textView=findViewById(R.id.tv_skip);

        Typeface typeface= Typeface.createFromAsset(getAssets(), "fonts/my_font.otf");
        textView.setTypeface(typeface);
        textView.setText("GO TO HOME");

        adapter= new IntroSliderAdapter(this);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(adapter);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroSlideActivity.this, BeforeLoginActivity.class));
                changeFirstInstallBool();
                finish();
            }
        });

    }
    private void changeFirstInstallBool(){
        editor.putBoolean(firstInstall, false).commit();
    }
}
