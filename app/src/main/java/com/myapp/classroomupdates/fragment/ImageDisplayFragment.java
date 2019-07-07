package com.myapp.classroomupdates.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDisplayFragment extends Fragment {

    private ImageView imageView;
    private Button button;
    private Bundle bundle;
    private Bitmap bitmap;
    private OnFragmentClickListener listener;

    public ImageDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            listener= (OnFragmentClickListener) context;
        }
        bundle= getArguments();
        byte[] byteArray= bundle.getByteArray("imageByte");
        bitmap= byteToBitmap(byteArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= LayoutInflater.from(getContext()).inflate(R.layout.just_an_image_view, container, false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView= view.findViewById(R.id.iv_change_image);
        button= view.findViewById(R.id.btn_change_image);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView.setImageBitmap(bitmap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExternalCardWritePermission();
                if (hasWritePermissions()) {
                    Globals.saveBitmapToCard(bitmap);
                }
                else {
                    Toast.makeText(getContext(), "No write permissions!!", Toast.LENGTH_SHORT).show();
                }
                listener.onFragmentClicked(new Bundle(), button.getId());
                //TODO upload image to server
            }
        });
    }

    private Bitmap byteToBitmap(byte[] byteArray){
        Bitmap bitmap= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    private boolean hasWritePermissions(){
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) {
            return false;
        }
        else {
            return true;
        }
    }

    public void getExternalCardWritePermission(){
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
}
