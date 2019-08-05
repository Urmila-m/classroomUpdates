package com.myapp.classroomupdates;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapp.classroomupdates.fragment.ImageDisplayFragment;
import com.myapp.classroomupdates.interfaces.ApiInterface;
import com.myapp.classroomupdates.model.StudentModel;
import com.myapp.classroomupdates.model.TeacherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class Globals extends Application {
    public static String firstInstall="firstInstall";
    public static ApiInterface apiInterface;
    public  static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    private static Gson gson;
    
    @Override
    public void onCreate() {
        super.onCreate();
        preferences= getSharedPreferences("loggedInUser", MODE_PRIVATE);
        editor= preferences.edit();
        gson= new Gson();
        apiInterface= RetrofitClient.getRetrofitObj().create(ApiInterface.class);
    }

    public static void showSnackbar(View view, String msg){
        Snackbar snackbar= Snackbar.make(view, "No internet connection! "+msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getContext().getColor(R.color.grey));
        snackbar.setActionTextColor(view.getContext().getColor(R.color.yellow));
        snackbar.show();
    }

    public static void showSthWentWrong(Context context){
        Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_LONG).show();
    }

    public static void saveBitmapToCard(Bitmap imageSelected){
        File file=new File(Environment.getExternalStorageDirectory().getPath(), "Classroom Updates");
        if (!file.exists()){
            Log.e("TAG", "writeToExternalStorage: file doesnt exist" );
            file.mkdir();
        }
        if (file.exists()) {
            Log.e("TAG", "writeToExternalStorage: imageInsertion");
            File exactImageLocation=new File(file, "test"+".jpeg");//TODO set email instead of test
            try {

                OutputStream outputStream=new FileOutputStream(exactImageLocation);
                imageSelected.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isEmailValid(CharSequence email){
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern= Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmpty(String text){
        if (text.length()==0){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isthreeNumberFormat(CharSequence text){
        String REGEX= ".*\\d.*.*\\d.*.*\\d.*";
        Pattern pattern= Pattern.compile(REGEX);
        Matcher matcher= pattern.matcher(text);
        return matcher.matches();
    }

    public static String getStringFromTIL(TextInputLayout layout){
        String text= layout.getEditText().getText().toString();
        return text;
    }

    public static boolean isGroupFormat(CharSequence group){
        String REGEX= "A|B|C|D|E|F";
        Pattern pattern= Pattern.compile(REGEX);
        Matcher matcher= pattern.matcher(group);
        return matcher.matches();

    }

    public static void saveUserToPreference(StudentModel student, String token, int id, String image){
        editor.putString("user_type", "Student");
        editor.putInt("id", id);
        editor.putString("Student", toJson(student));
        editor.putString("token", token);
        editor.putString("image", image);
        editor.commit();
    }

    public static void saveUserToPreference(TeacherModel teacher, String token, int id, String image){
        editor.putString("user_type", "Teacher");
        editor.putString("Teacher", toJson(teacher));
        editor.putInt("id", id);
        editor.putString("image", image);
        editor.putString("token", token);
        editor.commit();
    }

    public static String getTodaysDay(){
        Calendar calendar= Calendar.getInstance();
        Date currentDate= calendar.getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE");
        String day= dateFormat.format(currentDate);
        return day;
    }

    public static String getTodaysDateStringFormat(){
        Calendar calendar= Calendar.getInstance();
        Date currentDate= calendar.getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String date= dateFormat.format(currentDate);
        return date;
    }

    public static String toJson(StudentModel student){
        String studentJsonModel= gson.toJson(student);
        return studentJsonModel;
    }

    public static String toJson(TeacherModel teacher){
        String teacherJsonModel= gson.toJson(teacher);
        return teacherJsonModel;
    }

    public static StudentModel fromJsonToStudent(String json){
        StudentModel student= gson.fromJson(json, StudentModel.class);
        return student;
    }

    public static TeacherModel fromJsonToTeacher(String json){
        TeacherModel teacher= gson.fromJson(json, TeacherModel.class);
        return teacher;
    }

    public static Bundle convertPathToBundle(Context context, Uri path) throws IOException {
        Bitmap imageSelected= MediaStore.Images.Media.getBitmap(context.getContentResolver(), path);
        byte [] imageByte= bitmapToByte(imageSelected);
        Bundle imageBundle= new Bundle();
        imageBundle.putString("imageByte", byteArrayToString(imageByte));
        return imageBundle;
    }

    public Bundle convertIntentDataBundle(Intent data){
        Bundle extras = data.getExtras();
        Bitmap imageSelected = (Bitmap) extras.get("data");
        byte[] imageByte= bitmapToByte(imageSelected);
        Bundle imageBundle= new Bundle();
        imageBundle.putByteArray("imageByte", imageByte);
        return imageBundle;
    }

    private static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByte= stream.toByteArray();
        return imageByte;
    }

    public static Bitmap byteToBitmap(byte[] byteArray){
        Bitmap bitmap= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    public static String byteArrayToString(byte[] byteArray){
        String encodedString= Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedString;
    }

    public static String bitmapToEncodedString(Bitmap bitmap){
        byte[] byteArray= bitmapToByte(bitmap);
        String encodedString= byteArrayToString(byteArray);
        return encodedString;
    }

    public static Bitmap encodedStringToBitmap(String encodedString){
        byte[] byteArray= Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap= byteToBitmap(byteArray);
        return bitmap;
    }

}
