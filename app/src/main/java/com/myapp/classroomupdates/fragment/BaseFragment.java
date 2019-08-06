package com.myapp.classroomupdates.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public AlertDialog buildAlertDialog(AlertDialog.Builder builder){
        AlertDialog dialog= builder.
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
                .setMessage("Don't select images with size bigger than 2MB")
                .create();
        dialog.show();
        return dialog;
    }

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByte= stream.toByteArray();
        return imageByte;
    }

    protected Bitmap byteToBitmap(byte[] byteArray){
        Bitmap bitmap= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    private String byteArrayToString(byte[] byteArray){
        String encodedString= Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedString;
    }

    protected String bitmapToEncodedString(Bitmap bitmap){
        byte[] byteArray= bitmapToByte(bitmap);
        String encodedString= byteArrayToString(byteArray);
        return encodedString;
    }

    protected ImageDisplayFragment convertPathToFragment(Uri path) throws IOException {
        Bitmap imageSelected= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
        byte [] imageByte= bitmapToByte(imageSelected);
        Bundle imageBundle= new Bundle();
        imageBundle.putByteArray("imageByte", imageByte);
        ImageDisplayFragment fragment= new ImageDisplayFragment();
        fragment.setArguments(imageBundle);
        return fragment;
    }

    protected ImageDisplayFragment convertIntentDataToFragment(Intent data){
        Bundle extras = data.getExtras();
        Bitmap imageSelected = (Bitmap) extras.get("data");
        byte[] imageByte= bitmapToByte(imageSelected);
        Bundle imageBundle= new Bundle();
        imageBundle.putByteArray("imageByte", imageByte);
        ImageDisplayFragment fragment= new ImageDisplayFragment();
        fragment.setArguments(imageBundle);
        return fragment;
    }

}
