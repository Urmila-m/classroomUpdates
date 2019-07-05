package com.myapp.classroomupdates.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.interfaces.OnFragmentClickListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.myapp.classroomupdates.Globals.SELECT_IMAGE;
import static com.myapp.classroomupdates.Globals.TAKE_PHOTO;

public class StudentProfileFragment extends Fragment {

    private TextView tvName, tvRoll, tvSemester, tvEmail;
    private CircleImageView ivStudent;
    private Bundle bundle, imageBundle;
    private Bitmap imageSelected;
    private OnFragmentClickListener listener;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.student_profile, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener){
            listener= (OnFragmentClickListener) context;
        }
        bundle= getArguments();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvEmail= view.findViewById(R.id.tv_student_email);
        tvName= view.findViewById(R.id.tv_student_name);
        tvRoll= view.findViewById(R.id.tv_roll);
        tvSemester= view.findViewById(R.id.tv_semester);
        ivStudent= view.findViewById(R.id.iv_student_profile);

        builder= new AlertDialog.Builder(getContext());
        imageBundle= new Bundle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (bundle!=null){
            tvName.setText(bundle.getString("name"));
            tvEmail.setText(bundle.getString("email"));
            tvSemester.setText(bundle.getString("batch"));//TODO calculate sem from batch and display
            tvRoll.setText(bundle.getString("roll"));

            //TODO to set image from server, use picasso
            Picasso.get().load("").placeholder(R.drawable.portrait).into(ivStudent);
        }

        ivStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog= builder.
                        setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takePhoto();
                            }
                        })
                        .setNegativeButton("Choose from gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectImage();
                            }
                        })
                        .setTitle("Want to change your picture?")
                        .setMessage("Dont select images with size bigger than 2MB")
                        .create();
                dialog.show();

            }
        });
    }

    private void selectImage(){
        Intent selectImage= new Intent();
        selectImage.setType("image/*");
        selectImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(selectImage, 1);
    }

    private void takePhoto(){
        Intent takePhoto= new Intent();
        takePhoto.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePhoto, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: called");
        if (requestCode==1 && resultCode== RESULT_OK && data!=null){
            Log.e("TAG", "onActivityResult: result ok");
            Uri path= data.getData();
            try {
                imageSelected= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                byte [] imageByte= bitmapToByte(imageSelected);
                imageBundle.putByteArray("imageByte", imageByte);
                listener.onFragmentClicked(imageBundle, SELECT_IMAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode==2 && resultCode== RESULT_OK && data!=null){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            byte[] imagebyte= bitmapToByte(imageBitmap);
            imageBundle.putByteArray("imageByte", imagebyte);
            listener.onFragmentClicked(imageBundle, TAKE_PHOTO);
        }
    }

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByte= stream.toByteArray();
        return imageByte;
    }
}
