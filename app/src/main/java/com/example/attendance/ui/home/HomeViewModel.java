package com.example.attendance.ui.home;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.attendance.MainActivity;

import java.util.ArrayList;

import com.example.attendance.CalendarQuickstart;
import com.example.attendance.R;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ListView listView;
    public HomeViewModel() {
        CalendarQuickstart c = new CalendarQuickstart();
        mText = new MutableLiveData<>();
       // mText.setValue(c.tesst());
    }
    public LiveData<String> getText() {
        return mText;
    }
}