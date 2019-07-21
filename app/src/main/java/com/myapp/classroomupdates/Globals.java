package com.myapp.classroomupdates;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Globals extends Application {
    public static String firstInstall="firstInstall";
    public static String GET_STUDENT_HOME="getStudentHomeModelList";
    public static String GET_DAILY_STUDENT_SCHEDULE="GetDailyStudentSchedule";
    public static String GET_DAILY_TEACHER_SCHEDULE="GetDailyTeacherSchedule";
    public static String GET_STUDENT="GetStudentModel";
    public static String GET_TEACHER="GetTeacherModel";
    public static String CHANGE_PASSWORD="ChangePassword";
    public static String GET_FEEDBACK="GetFeedback";
    public static String SEND_FEEDBACK="SendFeedback";
    public static String REGISTER_STUDENT="RegisterStudent";
    public static String CHANGE_PICTURE="ChangePicture";
    public static String GET_ATTENDANCE="GetAttendance";
    public static String REGISTER_TEACHER="RegisterTeacher";
    public static String POST_TEACHER_ATTEND="PostTeacherAttend";

    public static boolean IS_SEMESTER_END= true;
    public static int TAKE_PHOTO = 100;
    public static int SELECT_IMAGE = 101;
    public static Context context;
    public static View view;

    @Override
    public void onCreate() {
        super.onCreate();
        context= getApplicationContext();
        view= new View(context);
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
}
