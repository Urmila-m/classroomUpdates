package com.myapp.classroomupdates.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.fragment.ScheduleFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScheduleViewPagerAdapter extends FragmentPagerAdapter{
    private TreeMap<Date, ScheduleFragment> fragmentList;
    private Context context;
    private List<Date> dateList;

    public ScheduleViewPagerAdapter(FragmentManager fm, Context context, TreeMap<Date, ScheduleFragment> list) {
        super(fm);
        fragmentList= list;
        this.context= context;
        dateList= new ArrayList<>();
        for (Map.Entry<Date, ScheduleFragment> m:fragmentList.entrySet()
             ) {
            dateList.add(m.getKey());
        }
    }

    @Override
    public Fragment getItem(int i) {
        Log.e("TAG", "getItem: "+fragmentList.get(dateList.get(i)).toString());
        return fragmentList.get(dateList.get(i));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Globals.getDay(dateList.get(position));
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void clearAll(){
        fragmentList.clear();
        notifyDataSetChanged();
    }
}
