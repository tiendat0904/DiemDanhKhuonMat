package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CustomRepeat extends AppCompatActivity {
    TextView soluong;
    Button btn_cong,btn_tru;
    Toolbar toolbar;
    int dem=1;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_repeat);
        soluong = (TextView)findViewById(R.id.textView_amount);
        btn_cong = (Button)findViewById(R.id.button_cong);
        btn_tru = (Button)findViewById(R.id.button_tru);
        toolbar = (Toolbar)findViewById(R.id.toolbar_custom_repeat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        soluong.setText(dem+"");
        btn_tru.setVisibility(View.INVISIBLE);

        btn_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dem++;
                if(dem==100000)
                {
                    soluong.setText(dem+"");
                    btn_cong.setVisibility(View.INVISIBLE);
                }
                else{
                    btn_tru.setVisibility(View.VISIBLE);
                    soluong.setText(dem+"");
                }

            }
        });
        btn_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dem--;
                if(dem==1)
                {
                    soluong.setText(dem+"");
                    btn_tru.setVisibility(View.INVISIBLE);
                }
                else{
                    btn_cong.setVisibility(View.VISIBLE);

                    soluong.setText(dem+"");
                }
            }
        });
    }
}
