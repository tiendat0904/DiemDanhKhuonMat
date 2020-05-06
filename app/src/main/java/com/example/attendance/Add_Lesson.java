package com.example.attendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.API.Event_API;
import com.example.attendance.Model.Event_Post;
import com.example.attendance.Model.Shift;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Lesson extends AppCompatActivity {
    APIService mAPIService;
    TextView editText_ngay;
    String a;
    String luachonlap;
    String txtT2,txtT3,txtT4,txtT5,txtT6,txtT7,txtCN,kieulap,solanlapmoituan;
    //EditText editText_ngay;
    int ca,lop;
    DateTime dateTime =null;
    Button btn_chonlich,btn_xacnhan;
    Spinner spinner_luachon,spinner_lop,spinner_ca;
    ArrayList<String> list_luachon;
    ArrayList<SubjectClass> list_class = new ArrayList<>();
    SimpleDateFormat fmtDateAndTime = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> get_sujectclass,get_shift;
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
        editText_ngay= (TextView) findViewById(R.id.editText_lich);
        btn_chonlich = (Button)findViewById(R.id.button_chonlich);
        btn_xacnhan =(Button)findViewById(R.id.button_xacnhan);
        spinner_luachon =(Spinner)findViewById(R.id.spinner_luachon);
        spinner_lop = (Spinner)findViewById(R.id.spinner_lop);
        spinner_ca= (Spinner)findViewById(R.id.spinner_ca);
        list_luachon = new ArrayList<>();
        list_luachon.add("");
        list_luachon.add("Hàng Ngày");
        list_luachon.add("Hàng Tuần");
        list_luachon.add("Tùy Chỉnh");
        get_sujectclass = new ArrayList<>();
        spinner_lop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lop=position;
                Toast.makeText(getApplicationContext(),lop+"",Toast.LENGTH_LONG).show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        get_shift = new ArrayList<>();
        get_Class();
        get_Shift();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list_luachon);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_luachon.setAdapter(arrayAdapter);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
                Intent intent = new Intent(Add_Lesson.this,MainActivity.class);
                startActivity(intent);
            }
        });
        spinner_ca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ca=position;
                Toast.makeText(getApplicationContext(),ca+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_luachon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(spinner_luachon.getSelectedItem()=="Hàng Tuần")
                {
                    luachonlap=spinner_luachon.getSelectedItem().toString();

                }
                if(spinner_luachon.getSelectedItem()=="Hàng Ngày")
                {
                    luachonlap=spinner_luachon.getSelectedItem().toString();
                }
                if(spinner_luachon.getSelectedItem()=="Tùy Chỉnh")
                {
                    luachonlap=spinner_luachon.getSelectedItem().toString();
                    Intent intent = new Intent(Add_Lesson.this,CustomRepeat.class);
                    startActivityForResult(intent,9);

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
        String a = fmtDateAndTime.format(myCalendar.getTime());
        editText_ngay.setText(a);
        editText_ngay.getText().toString();
        //Toast.makeText(getApplicationContext(),b,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9) {
            txtT2 = data.getStringExtra("T2");
            txtT3 = data.getStringExtra("T3");
            txtT4 = data.getStringExtra("T4");
            txtT5 = data.getStringExtra("T5");
            txtT6 = data.getStringExtra("T6");
            txtT7 = data.getStringExtra("T7");
            txtCN = data.getStringExtra("CN");
            kieulap = data.getStringExtra("kieulap");
            solanlapmoituan = data.getStringExtra("laptuan");
        }
    }

    private void addEvent() {
        Date hientai=null;
        Date cuoicung=null;
        if(luachonlap=="Hàng Tuần")
        {
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            Date date1=null;
            try {
                date =format.parse(editText_ngay.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c1= Calendar.getInstance();
            c1.setTime(date);
            hientai=c1.getTime();
            c1.add(Calendar.YEAR,1);
            cuoicung=c1.getTime();
            dateTime=DateTime.parseRfc3339(editText_ngay.getText().toString());
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.3.2:64535/api/").client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Event_API event_api =retrofit.create(Event_API.class);
            String json ="{\n" +
                    "    \"shiftID\": "+ca+",\n" +
                    "    \"subjectClassID\":"+lop+",\n" +
                    "    \"dateTime\": \""+dateTime+"\"\n" +
                    "}";
            RequestBody requestBody =RequestBody.create(MediaType.parse("application/json"),json);
            event_api.Postdata(requestBody).enqueue(new Callback<Event_Post>() {
                @Override
                public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                }
                @Override
                public void onFailure(Call<Event_Post> call, Throwable t) {

                }
            });
            while (hientai.before(cuoicung))
            {
                c1.setTime(date);
                c1.add(Calendar.DATE,7);
                String a = format.format(c1.getTime());
                hientai = c1.getTime();
                date=c1.getTime();
                if(hientai.after(cuoicung))
                {
                    break;
                }
                dateTime=DateTime.parseRfc3339(a);
                String json1 ="{\n" +
                        "    \"shiftID\": "+ca+",\n" +
                        "    \"subjectClassID\":"+lop+",\n" +
                        "    \"dateTime\": \""+dateTime+"\"\n" +
                        "}";
                RequestBody requestBody1 =RequestBody.create(MediaType.parse("application/json"),json1);
                event_api.Postdata(requestBody1).enqueue(new Callback<Event_Post>() {
                    @Override
                    public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                    }
                    @Override
                    public void onFailure(Call<Event_Post> call, Throwable t) {

                    }
                });
            }
        }
        else if(luachonlap=="Hàng Ngày")
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            Date date1=null;
            try {
                date =format.parse(editText_ngay.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c1= Calendar.getInstance();
            c1.setTime(date);
            hientai=c1.getTime();
            c1.add(Calendar.YEAR,1);
            cuoicung=c1.getTime();
            dateTime=DateTime.parseRfc3339(editText_ngay.getText().toString());
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.3.2:64535/api/").client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Event_API event_api =retrofit.create(Event_API.class);
            String json ="{\n" +
                    "    \"shiftID\": "+ca+",\n" +
                    "    \"subjectClassID\":"+lop+",\n" +
                    "    \"dateTime\": \""+dateTime+"\"\n" +
                    "}";
            RequestBody requestBody =RequestBody.create(MediaType.parse("application/json"),json);
            event_api.Postdata(requestBody).enqueue(new Callback<Event_Post>() {
                @Override
                public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                }
                @Override
                public void onFailure(Call<Event_Post> call, Throwable t) {

                }
            });
            while (hientai.before(cuoicung))
            {
//                try {
//                    date =format.parse(editText_ngay.getText().toString());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                c1.setTime(date);
                c1.add(Calendar.DATE,1);
                String a = format.format(c1.getTime());
                hientai = c1.getTime();
                date=c1.getTime();
                if(hientai.after(cuoicung))
                {
                    break;
                }
                dateTime=DateTime.parseRfc3339(a);
                String json1 ="{\n" +
                        "    \"shiftID\": "+ca+",\n" +
                        "    \"subjectClassID\":"+lop+",\n" +
                        "    \"dateTime\": \""+dateTime+"\"\n" +
                        "}";
                RequestBody requestBody1 =RequestBody.create(MediaType.parse("application/json"),json1);
                event_api.Postdata(requestBody1).enqueue(new Callback<Event_Post>() {
                    @Override
                    public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                    }
                    @Override
                    public void onFailure(Call<Event_Post> call, Throwable t) {

                    }
                });
            }
        }
        else if(luachonlap=="Tùy Chỉnh")
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            Date date1=null;
            try {
                date =format.parse(editText_ngay.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c1= Calendar.getInstance();
            c1.setTime(date);
            int thungay =c1.get(Calendar.DAY_OF_WEEK);
            hientai=c1.getTime();
            c1.add(Calendar.YEAR,1);
            cuoicung=c1.getTime();
            dateTime=DateTime.parseRfc3339(editText_ngay.getText().toString());
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.3.2:64535/api/").client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Event_API event_api =retrofit.create(Event_API.class);
            String json ="{\n" +
                    "    \"shiftID\": "+ca+",\n" +
                    "    \"subjectClassID\":"+lop+",\n" +
                    "    \"dateTime\": \""+dateTime+"\"\n" +
                    "}";
            RequestBody requestBody =RequestBody.create(MediaType.parse("application/json"),json);
            event_api.Postdata(requestBody).enqueue(new Callback<Event_Post>() {
                @Override
                public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                }
                @Override
                public void onFailure(Call<Event_Post> call, Throwable t) {

                }
            });
            while (hientai.before(cuoicung))
            {
                c1.setTime(date);
                c1.add(Calendar.DATE,1);
                String a = format.format(c1.getTime());
                hientai = c1.getTime();
                date=c1.getTime();
                if(hientai.after(cuoicung))
                {
                    break;
                }
                dateTime=DateTime.parseRfc3339(a);
                String json1 ="{\n" +
                        "    \"shiftID\": "+ca+",\n" +
                        "    \"subjectClassID\":"+lop+",\n" +
                        "    \"dateTime\": \""+dateTime+"\"\n" +
                        "}";
                RequestBody requestBody1 =RequestBody.create(MediaType.parse("application/json"),json1);
                event_api.Postdata(requestBody1).enqueue(new Callback<Event_Post>() {
                    @Override
                    public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                    }
                    @Override
                    public void onFailure(Call<Event_Post> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void get_Shift() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:64535/api/").client(okHttpClient)
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
                List<Shift> shifts = response.body();
                get_shift.add("");
                for(Shift shift : shifts){
                    get_shift.add(shift.getShiftName());
                }
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(Add_Lesson.this,android.R.layout.simple_spinner_item,get_shift);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_ca.setAdapter(arrayAdapter2);
            }

            @Override
            public void onFailure(Call<List<Shift>> call, Throwable t) {
                Log.d("bbbb", t.getCause().getMessage());
            }
        });
    }

    private void get_Class() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:64535/api/").client(okHttpClient)
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
                List<SubjectClass> classes = response.body();
                get_sujectclass.add("");
                for(SubjectClass classes1 : classes){
                    get_sujectclass.add(classes1.getSubjectClassName());
                }
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(Add_Lesson.this,android.R.layout.simple_spinner_item,get_sujectclass);
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_lop.setAdapter(arrayAdapter1);
            }

            @Override
            public void onFailure(Call<List<SubjectClass>> call, Throwable t) {
                Log.d("aaa", t.getCause().getMessage());
            }
        });
    }
}
