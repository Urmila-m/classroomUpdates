package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.myapp.classroomupdates.fragment.StudentHomePageFragment;
import com.myapp.classroomupdates.fragment.TeacherAttendFragment;

import java.util.List;

public class StudentHomeViewPagerAdapter extends FragmentPagerAdapter {
    List<StudentHomePageFragment> list1;
    List<TeacherAttendFragment> list2;
    Context context;

    public StudentHomeViewPagerAdapter(FragmentManager fm, List<StudentHomePageFragment> list, Context context) {
        super(fm);
        this.list1 = list;
        this.context = context;
    }

    public StudentHomeViewPagerAdapter(FragmentManager fm, Context context, List<TeacherAttendFragment> list2) {
        super(fm);
        this.list2 = list2;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (list1!=null) {
            fragment= list1.get(i);
        }
        else if (list2!=null){
            fragment= list2.get(i);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        int size = 0;
        if (list1!=null) {
            size= list1.size();
        }
        else if (list2!=null){
            size= list2.size();
        }
        return size;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "class "+(position+1);
    }
}
