package com.example.attendance.ui.Other;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.ui.Other.CalendarQuickstart;
import android.os.Bundle;
import android.widget.TextView;

public class calendarWeek extends AppCompatActivity {
    TextView viTri, batDau, ketThuc, monHoc;
    CalendarQuickstart c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_week);
        anhXa();
        CalendarQuickstart calendarQuickstart = new CalendarQuickstart();
       // viTri.setText(calendarQuickstart.tesst());

    }

    public void anhXa() {
        viTri = (TextView)findViewById(R.id.ViTri);
        batDau = (TextView)findViewById(R.id.batDau);
        ketThuc = (TextView)findViewById(R.id.ketThuc);
        monHoc = (TextView)findViewById(R.id.monHoc);

    }
}
