package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.IntroSlideActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class IntroSliderAdapter extends PagerAdapter {

    private Context context;
    private TextView tvTitle, tvDescription;
    private CircleImageView imageView;

    public IntroSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view== (LinearLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.intro_screen_1, container,false);
        tvTitle=view.findViewById(R.id.tv_intro_title);
        tvDescription= view.findViewById(R.id.tv_intro_description);
        imageView= view.findViewById(R.id.iv_intro);

        Typeface typeface= Typeface.createFromAsset(context.getAssets(), "fonts/my_font.otf");
        tvTitle.setTypeface(typeface);
        tvDescription.setTypeface(typeface);

        if (position==0){
            imageView.setImageDrawable(context.getDrawable(R.drawable.updates));
            tvTitle.setText("Class Updates");
            tvDescription.setText("Get regular class updates directly from teacher!");

        }
        else if(position==1){
            imageView.setImageDrawable(context.getDrawable(R.drawable.schedule));
            tvTitle.setText("Schedule");
            tvDescription.setText("Class schedule right in your pocket!!");

        }
        else {
            imageView.setImageDrawable(context.getDrawable(R.drawable.attendance3));
            tvTitle.setText("Attendance");
            tvDescription.setText("Know your attenddance right away!!!");

        }

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
