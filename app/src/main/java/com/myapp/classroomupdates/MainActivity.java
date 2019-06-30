package com.myapp.classroomupdates;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.myapp.classroomupdates.fragment.ChangePasswordFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

        frameLayout= findViewById(R.id.fl_before_login);
//        Fragment fragment= new TeacherProfileFragment();
//        ArrayList<String> subjectList= new ArrayList<String>();
//        subjectList.add("Basic Electronics");
//        subjectList.add("Basic Electrical");
//        subjectList.add("Artificial Intelligence");
//        subjectList.add("Embedded System");
//
//        Bundle bundle= new Bundle();
//        bundle.putStringArrayList("subjectList", subjectList);
//        fragment.setArguments(bundle);
        setFragment(new ChangePasswordFragment(), "0");

    }

    public void setFragment(Fragment fragment, String backstack){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        if (backstack=="1"){
            transaction.addToBackStack("");
        }
        transaction.commit();
    }

}
