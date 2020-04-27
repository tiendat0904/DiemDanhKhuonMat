package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Add_Lesson extends AppCompatActivity {
    EditText editText_ngay;
    Button btn_chonlich;
    Spinner spinner_luachon;
    ArrayList<String> list_luachon;
    SimpleDateFormat fmtDateAndTime = new SimpleDateFormat("dd/MM/yyyy");
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update();
        }
    };

    private void update(){
        editText_ngay.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__lesson);
        editText_ngay= (EditText) findViewById(R.id.editText_lich);
        btn_chonlich = (Button)findViewById(R.id.button_chonlich);
        spinner_luachon =(Spinner)findViewById(R.id.spinner_luachon);
        list_luachon = new ArrayList<>();
        list_luachon.add("");
        list_luachon.add("Hàng Ngày");
        list_luachon.add("Hàng Tuần");
        list_luachon.add("Tất cả ngày trong tuần");
        list_luachon.add("Tùy Chỉnh");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list_luachon);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_luachon.setAdapter(arrayAdapter);
        spinner_luachon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_luachon.getSelectedItem()=="Tùy Chỉnh")
                {
                    Intent intent = new Intent(Add_Lesson.this,CustomRepeat.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_chonlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Add_Lesson.this, d,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        update();
    }
}
