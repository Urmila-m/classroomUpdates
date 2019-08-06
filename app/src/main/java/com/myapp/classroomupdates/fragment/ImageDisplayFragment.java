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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.myapp.classroomupdates.Globals;
import com.myapp.classroomupdates.R;
import com.myapp.classroomupdates.activity.AfterLoginActivityStudent;
import com.myapp.classroomupdates.activity.AfterLoginTeacherActivity;
import com.myapp.classroomupdates.activity.PreferenceInitializingActivity;
import com.myapp.classroomupdates.interfaces.OnDataRetrievedListener;
import com.myapp.classroomupdates.model.ImageUploadResponseModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.classroomupdates.Globals.apiInterface;
import static com.myapp.classroomupdates.Globals.editor;
import static com.myapp.classroomupdates.Globals.preferences;
import static com.myapp.classroomupdates.Globals.showSnackbar;
import static com.myapp.classroomupdates.Globals.showSthWentWrong;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDisplayFragment extends BaseFragment implements OnDataRetrievedListener {

    private ImageView imageView;
    private Button button;
    private Bundle bundle;
    private Bitmap bitmap;
    private FrameLayout frameLayout;

    public ImageDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null) {
            bundle = getArguments();
            byte[] byteArray = bundle.getByteArray("imageByte");
            bitmap = byteToBitmap(byteArray);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= LayoutInflater.from(getContext()).inflate(R.layout.just_an_image_view, container, false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("TAG", "onViewCreated: " );
        imageView= view.findViewById(R.id.iv_change_image);
        button= view.findViewById(R.id.btn_change_image);
        if (getContext() instanceof AfterLoginActivityStudent) {
            frameLayout = ((AfterLoginActivityStudent) getContext()).getFrameLayout();
        }
        else if (getContext() instanceof AfterLoginTeacherActivity){
            frameLayout= ((AfterLoginTeacherActivity) getContext()).getFrameLayout();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TAG", "onActivityCreated: ");
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
                Log.e("TAG", "onClick: test test test" );
                apiInterface.uploadImage("Token "+preferences.getString("token", ""), "data:image/png;base64,"+bitmapToEncodedString(bitmap))
                        .enqueue(new Callback<ImageUploadResponseModel>() {
                            @Override
                            public void onResponse(Call<ImageUploadResponseModel> call, Response<ImageUploadResponseModel> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getContext(), response.body().getDetail(), Toast.LENGTH_LONG).show();
                                    editor.putString("image", response.body().getUrl()).commit();
                                    OnDataRetrievedListener listener= ImageDisplayFragment.this;
                                    Fragment fragment = null;
                                    if (getContext() instanceof AfterLoginActivityStudent) {
                                        fragment= new StudentProfileFragment();
                                    }
                                    else if (getContext() instanceof AfterLoginTeacherActivity){
                                        fragment= new TeacherProfileFragment();
                                    }
                                    listener.onDataRetrieved(fragment, frameLayout, "uploadImage");
                                }
                                else {
                                    try {
                                        Log.e("TAG", "onResponse: "+response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    showSthWentWrong(getContext());
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

    @Override
    public void onDataRetrieved(Fragment fragment, FrameLayout frameLayout, String source) {
        Log.e("TAG", "onDataRetrieved: ");
        CircleImageView imageView= null;
        if (getContext() instanceof AfterLoginTeacherActivity){
            imageView= ((AfterLoginTeacherActivity)getContext()).getHeaderImage();
        }
        else if (getContext() instanceof AfterLoginActivityStudent){
            imageView= ((AfterLoginActivityStudent)getContext()).getHeaderImage();
        }
        Picasso.get().load(preferences.getString("image", "http://")).placeholder(R.drawable.portrait).into(imageView);
        ((PreferenceInitializingActivity)getContext()).setFragment(frameLayout, fragment, "0");
    }
}
