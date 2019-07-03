package com.myapp.classroomupdates.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.myapp.classroomupdates.fragment.StudentScheduleFragment;
import com.myapp.classroomupdates.fragment.TeacherScheduleFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<StudentScheduleFragment> fragmentList1;
    private List<TeacherScheduleFragment> fragmentList2;

    public ViewPagerAdapter(FragmentManager fm, List<StudentScheduleFragment> list) {
        super(fm);
        this.fragmentList1 = list;
    }

    public ViewPagerAdapter(List<TeacherScheduleFragment> list, FragmentManager fm) {
        super(fm);
        this.fragmentList2 = list;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (fragmentList1!=null){
            fragment= fragmentList1.get(i);

        }
        else if (fragmentList2!=null){
            fragment= fragmentList2.get(i);
        }
        return fragment;
    }


    @Override
    public int getCount() {
//        return fragmentList1.size();
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Sun";
        }
        else if (position==1){
            return "Mon";
        }

        else if (position==2){
            return "Tues";
        }
        else if (position==3){
            return "Wed";
        }
        else if (position==4){
            return "Thu";
        }
        else {
            return "Fri";
        }
    }
}
