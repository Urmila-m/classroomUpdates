package com.myapp.classroomupdates.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.myapp.classroomupdates.fragment.FirstPageFragment;
import com.myapp.classroomupdates.fragment.ScheduleFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<ScheduleFragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<ScheduleFragment> list) {
        super(fm);
        this.fragmentList= list;
    }

    @Override
    public Fragment getItem(int i) {
        Log.e("TAG", fragmentList.get(i).getArguments().toString() );
        if (fragmentList.get(i)!=null){
            Log.e("TAG", "getItem: not null"+i);
        }
        return fragmentList.get(i);
//        return new FirstPageFragment();
    }


    @Override
    public int getCount() {
        return fragmentList.size();
//        return 3;
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
