package com.myapp.classroomupdates.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

//required to set fragment. listener required because commiting a transaction inside an async thread
// throws exception as the thread has no knowledge of the activity state. So first we return to our main thread from the async
// thread using this interface.
public interface OnDataRetrievedListener {
    public void onDataRetrieved(Fragment fragment, FrameLayout frameLayout, String source);
}
