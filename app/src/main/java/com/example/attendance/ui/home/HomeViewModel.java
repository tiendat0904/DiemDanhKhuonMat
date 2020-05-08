package com.example.attendance.ui.home;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.attendance.ui.Other.CalendarQuickstart;

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