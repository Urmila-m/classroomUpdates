package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.myapp.classroomupdates.fragment.StudentHomePageFragment;

import java.util.List;

public class StudentHomeViewPagerAdapter extends FragmentPagerAdapter {
    List<StudentHomePageFragment> list;
    Context context;

    public StudentHomeViewPagerAdapter(FragmentManager fm, List<StudentHomePageFragment> list, Context context) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "class "+(position+1);
    }
}
