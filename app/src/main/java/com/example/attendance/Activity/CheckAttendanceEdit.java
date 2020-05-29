package com.example.attendance.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.attendance.API.APIService;
import com.example.attendance.API.AttendanceDetailsService;
import com.example.attendance.API.Event_API;
import com.example.attendance.API.ShiftService;
import com.example.attendance.Common.Const;
import com.example.attendance.Model.Event_Post;
import com.example.attendance.Model.Shift;
import com.example.attendance.Model.SubjectClass;
import com.example.attendance.R;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckAttendanceEdit extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private AutoCompleteTextView dropDownText;
    ArrayList<String> subjectClassNameArrayList = new ArrayList<>();
    ArrayList<SubjectClass> subjectClassArrayList = new ArrayList<>();
    ArrayList<Shift> arrayListShift = new ArrayList<>();
    Spinner spinner_lop, spinner_ca;
    ArrayList<String> get_shift = new ArrayList<>();
    SimpleDateFormat fmtDateAndTime = new SimpleDateFormat("yyyy-MM-dd");
    int ca, lop, subjectClassDefaultSelection, shiftDefaultSelection;
    private Button btn_chonlich;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update();
        }
    };
    private TextView editText_ngay;
    private Button btn_confirm;

    private void update() {
        editText_ngay.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance_edit);
        Bundle extras = getIntent().getExtras();
        btn_chonlich = (Button) findViewById(R.id.button_chonlich);
        btn_confirm = (Button) findViewById((R.id.button_xacnhan));
        spinner_lop = (Spinner) findViewById(R.id.spinner_lop);
        spinner_ca = (Spinner) findViewById(R.id.spinner_ca);
        editText_ngay = (TextView) findViewById(R.id.editText_lich);
        editText_ngay.setText(extras.getString("datetime"));
        setSpinner();
        get_Shift();
        get_Class();
        spinner_lop.setSelection(subjectClassDefaultSelection);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(CheckAttendanceEdit.this,android.R.layout.simple_spinner_item, subjectClassNameArrayList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_lop.setAdapter(arrayAdapter1);
        btn_chonlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CheckAttendanceEdit.this, d,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shiftID = 0;
                int subjectClassID = 0;
                DateTime dateTime = DateTime.parseRfc3339(editText_ngay.getText().toString());
                //DateTime dateTime = DateTime.parseRfc3339("2020-05-06");
                for(Shift shift : arrayListShift){
                    if(shift.getShiftName() == spinner_ca.getItemAtPosition(ca)){
                        shiftID = Integer.parseInt(shift.getShiftID());
                    }
                }
                String a = (String) spinner_lop.getItemAtPosition(lop);
                for(SubjectClass subjectClass : subjectClassArrayList){
                    if(subjectClass.getSubjectClassName() == (String) spinner_lop.getItemAtPosition(lop)){
                        subjectClassID = subjectClass.getSubjectClassID();
                    }
                }
                String eventID = extras.getString("eventID");
                Event_Post eventPost = new Event_Post(
                        shiftID,
                        subjectClassID,
                        dateTime
                        );
                eventPost.setEventID(Integer.parseInt(eventID));
                OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME).client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                Event_API event_api = retrofit.create(Event_API.class);
                String json1 ="{\n" +
                        "    \"eventID\": "+eventID+",\n" +
                        "    \"shiftID\": "+shiftID+",\n" +
                        "    \"subjectClassID\":"+subjectClassID+",\n" +
                        "    \"dateTime\": \""+dateTime+"\"\n" +
                        "    \"status\": \""+1+"\"\n" +
                        "}";
                RequestBody requestBody1 =RequestBody.create(MediaType.parse("application/json"),json1);
                event_api.Update(Integer.parseInt(eventID), requestBody1).enqueue(new Callback<Event_API>() {
                    @Override
                    public void onResponse(Call<Event_API> call, Response<Event_API> response) {

                    }

                    @Override
                    public void onFailure(Call<Event_API> call, Throwable t) {

                    }
                });
            }
        });

    }
    private void get_Shift() {
        Bundle extras = getIntent().getExtras();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ShiftService class_Service =retrofit.create(ShiftService.class);
        Call<List<Shift>> call = class_Service.getShift();
        call.enqueue(new Callback<List<Shift>>() {
            @Override
            public void onResponse(Call<List<Shift>> call, Response<List<Shift>> response) {
                if(!response.isSuccessful()){
                    try {
                        Log.d("bbbb", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                arrayListShift= (ArrayList<Shift>) response.body();
                ArrayList<String> shiftNameArrayList =new ArrayList<String>() ;
                for(int i = 0; i< arrayListShift.size(); i++){
                    int shiftID = Integer.parseInt(arrayListShift.get(i).getShiftID());
                    if(shiftID == extras.getInt("shiftID")){
                        shiftDefaultSelection = i;
                    }
                    shiftNameArrayList.add(arrayListShift.get(i).getShiftName());
                }
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(CheckAttendanceEdit.this,android.R.layout.simple_spinner_item,shiftNameArrayList);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_ca.setAdapter(arrayAdapter2);
            }

            @Override
            public void onFailure(Call<List<Shift>> call, Throwable t) {
//                Log.d("bbbb", t.getCause().getMessage());
            }
        });
    }

    private void setSpinner(){
        spinner_lop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lop = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_ca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ca = position;
//                Toast.makeText(getApplicationContext(), ca + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void get_Class() {
        Bundle extras = getIntent().getExtras();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        APIService class_Service =retrofit.create(APIService.class);
        Call<List<SubjectClass>> call = class_Service.getCSubjectClass();
        call.enqueue(new Callback<List<SubjectClass>>() {
            @Override
            public void onResponse(Call<List<SubjectClass>> call, Response<List<SubjectClass>> response) {
                if(!response.isSuccessful()){
                    try {
                        Log.d("aaa", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                subjectClassArrayList= (ArrayList<SubjectClass>) response.body();
                for(int i = 0; i< subjectClassArrayList.size(); i++){
                    int subjectClassID = subjectClassArrayList.get(i).getSubjectClassID();
                    if(subjectClassID == extras.getInt("subjectClassID")){
                        subjectClassDefaultSelection = i;
                    }
                    subjectClassNameArrayList.add(subjectClassArrayList.get(i).getSubjectClassName());
                }
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(CheckAttendanceEdit.this,android.R.layout.simple_spinner_item,subjectClassNameArrayList);
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_lop.setAdapter(arrayAdapter1);
            }

            @Override
            public void onFailure(Call<List<SubjectClass>> call, Throwable t) {
//                Log.d("aaa", t.getCause().getMessage());
            }
        });
    }
}
