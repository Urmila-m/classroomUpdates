package com.myapp.classroomupdates.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.fragment.ImageDisplayFragment;
import com.myapp.classroomupdates.fragment.StudentHomePageFragment;
import com.myapp.classroomupdates.fragment.TeacherProfileFragment;
import com.myapp.classroomupdates.interfaces.OnDataRetrievedListener;
import com.myapp.classroomupdates.model.ImageUploadResponseModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.bitmapToEncodedString;
import static com.myapp.classroomupdates.Globals.byteToBitmap;
import static com.myapp.classroomupdates.Globals.editor;
import static com.myapp.classroomupdates.Globals.encodedStringToBitmap;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;
import static com.myapp.classroomupdates.Globals.showSthWentWrong;
import static java.security.AccessController.getContext;

public class ImageUploadActivity extends PreferenceInitializingActivity {

    private Button button;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onCreate: image");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_an_image_view);

        if (getIntent().getExtras()!=null) {
            Bundle bundle = getIntent().getBundleExtra("imageByte");
            String encodedString = bundle.getString("imageByte");
            bitmap = encodedStringToBitmap(encodedString);
        }

        ImageView imageView = findViewById(R.id.iv_change_image);
        button= findViewById(R.id.btn_change_image);

        imageView.setImageBitmap(bitmap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExternalCardWritePermission();
                if (hasWritePermissions()) {
                    Globals.saveBitmapToCard(bitmap);
                }
                else {
                    Toast.makeText(ImageUploadActivity.this, "No write permissions!!", Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", "onClick: test test test" );
                apiInterface.uploadImage("Token "+preferences.getString("token", ""), "data:image/png;base64,"+bitmapToEncodedString(bitmap))
                        .enqueue(new Callback<ImageUploadResponseModel>() {
                            @Override
                            public void onResponse(Call<ImageUploadResponseModel> call, Response<ImageUploadResponseModel> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(ImageUploadActivity.this, response.body().getDetail(), Toast.LENGTH_LONG).show();
                                    editor.putString("image", response.body().getUrl()).commit();
                                    OnDataRetrievedListener listener= ImageUploadActivity.this;
                                    listener.onDataRetrieved(new Fragment(), new FrameLayout(ImageUploadActivity.this), "uploadImage");
                                }
                                else {
                                    try {
                                        Log.e("TAG", "onResponse: "+response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    showSthWentWrong(ImageUploadActivity.this);
                                }

                            }

                            @Override
                            public void onFailure(Call<ImageUploadResponseModel> call, Throwable t) {
                                showSnackbar(button, "Image upload failed!");
                                Log.e("TAG", "onFailure: "+t.getMessage());
                            }
                        });
            }
        });
    }
    private boolean hasWritePermissions(){
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onDataRetrieved(Fragment fragment, FrameLayout frameLayout, String source) {
        super.onDataRetrieved(fragment, frameLayout, source);
        if (preferences.getString("user_type", "").equals("Student")){
            startActivity(new Intent(this, AfterLoginActivityStudent.class));
        }
        else if (preferences.getString("user_type", "").equals("Teacher")){
            startActivity(new Intent(this, AfterLoginTeacherActivity.class));
        }
        finish();
    }

    public void getExternalCardWritePermission(){
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: image" );
    }
}
