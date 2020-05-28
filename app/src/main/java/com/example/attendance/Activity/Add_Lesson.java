package com.example.attendance.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.attendance.API.Event_API;
import com.example.attendance.API.APIService;
import com.example.attendance.Common.Const;
import com.example.attendance.Model.Event_Post;
import com.example.attendance.Model.Shift;
import com.example.attendance.R;
import com.example.attendance.API.ShiftService;
import com.example.attendance.Model.SubjectClass;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.text.ParseException;
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

public class Add_Lesson extends AppCompatActivity {
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
//            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
//            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
//            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };
    private static final int PROJECTION_ID_INDEX = 0;
    //    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
//    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
//    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    GoogleApiClient googleApiClient;
    private  static final String TAG="SignInActivity";
    private static final int RC_SIGN_IN =9001;
    private int REQUEST_ID_IMAGE_CAPTURE = 100;
    private int REQUEST_ID_IMAGE_CAPTURE_write = 101;
    APIService mAPIService;
    TextView editText_ngay;
    //    String a;
////    String luachonlap;
    String txtT2, txtT3, txtT4, txtT5, txtT6, txtT7, txtCN, kieulap, solanlapmoituan, a, luachonlap, lophoc;
    int ca, lop;
    //    String lophoc;
    DateTime dateTime = null;
    Button btn_chonlich, btn_xacnhan, btn_x;
    Spinner spinner_luachon, spinner_lop, spinner_ca;
    ArrayList<String> list_luachon;
    ArrayList<Shift> arrayListShift = new ArrayList<>();
    ArrayList<SubjectClass> arrayListClass = new ArrayList<>();
    ArrayList<SubjectClass> list_class = new ArrayList<>();
    SimpleDateFormat fmtDateAndTime = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> get_sujectclass = new ArrayList<>();
    ArrayList<String> get_shift = new ArrayList<>();
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

    private void update() {
        editText_ngay.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__lesson);
        anhxa();
        setSpinner();
        get_Class();
        get_Shift();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_luachon);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_luachon.setAdapter(arrayAdapter);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
                Intent intent = new Intent(Add_Lesson.this, Login.class);
                startActivity(intent);

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

    private void setSpinner() {
        spinner_lop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lop = position;
                //     lophoc =arrayListClass.get(position).getSubjectClassName();
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
        spinner_luachon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (spinner_luachon.getSelectedItem() == "Hàng Tuần") {
                    luachonlap = spinner_luachon.getSelectedItem().toString();

                }
                if (spinner_luachon.getSelectedItem() == "Hàng Ngày") {
                    luachonlap = spinner_luachon.getSelectedItem().toString();
                }
                if (spinner_luachon.getSelectedItem() == "Tùy Chỉnh") {
                    luachonlap = spinner_luachon.getSelectedItem().toString();
                    Intent intent = new Intent(Add_Lesson.this, CustomRepeat.class);
                    startActivityForResult(intent, 9);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void anhxa() {
        editText_ngay = (TextView) findViewById(R.id.editText_lich);
        btn_chonlich = (Button) findViewById(R.id.button_chonlich);
        btn_xacnhan = (Button) findViewById(R.id.button_xacnhan);
        spinner_luachon = (Spinner) findViewById(R.id.spinner_luachon);
        spinner_lop = (Spinner) findViewById(R.id.spinner_lop);
        spinner_ca = (Spinner) findViewById(R.id.spinner_ca);
        btn_x = (Button) findViewById(R.id.button_x);
        btn_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list_luachon = new ArrayList<>();
        list_luachon.add("");
        list_luachon.add("Hàng Ngày");
        list_luachon.add("Hàng Tuần");
        list_luachon.add("Tùy Chỉnh");

    }


    private void verifyPermission() {
        final int permission_camera = ContextCompat.checkSelfPermission(Add_Lesson.this, Manifest.permission.READ_CALENDAR);
        final int permission_camera_write = ContextCompat.checkSelfPermission(Add_Lesson.this, Manifest.permission.WRITE_CALENDAR);
        if (permission_camera != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Add_Lesson.this, Manifest.permission.READ_CALENDAR)) {
                ActivityCompat.requestPermissions(Add_Lesson.this, new String[]{Manifest.permission.READ_CALENDAR},
                        REQUEST_ID_IMAGE_CAPTURE);
            } else {
                ActivityCompat.requestPermissions(Add_Lesson.this, new String[]{Manifest.permission.READ_CALENDAR},
                        REQUEST_ID_IMAGE_CAPTURE);
            }
        }
        if (permission_camera_write != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Add_Lesson.this, Manifest.permission.WRITE_CALENDAR)) {
                ActivityCompat.requestPermissions(Add_Lesson.this, new String[]{Manifest.permission.WRITE_CALENDAR},
                        REQUEST_ID_IMAGE_CAPTURE_write);
            } else {
                ActivityCompat.requestPermissions(Add_Lesson.this, new String[]{Manifest.permission.WRITE_CALENDAR},
                        REQUEST_ID_IMAGE_CAPTURE_write);
            }
        }
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
        Date hientai = null;
        Date cuoicung = null;
        long startMillis = 0;
        long endMillis = 0;
        Date startshift = null;
        Date endshiff = null;
        String millis_start = arrayListShift.get(ca).getShiftStart();
        String millis_end = arrayListShift.get(ca).getShiftEnd();
        int thu = 0;
        String thungay = "";
        if (luachonlap == "Hàng Tuần") {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//           SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd'T'");

            Date date = null;

            try {
                date = format.parse(editText_ngay.getText().toString());
                startshift = format.parse(editText_ngay.getText().toString() + "'T'" + millis_start);
                endshiff = format.parse(editText_ngay.getText().toString() + "'T'" + millis_end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(date);
//            startMillis=beginTime.getTimeInMillis();
            hientai = beginTime.getTime();

            beginTime.add(Calendar.YEAR, 1);
//            endMillis=beginTime.getTimeInMillis();
            cuoicung = beginTime.getTime();
            dateTime = DateTime.parseRfc3339(editText_ngay.getText().toString());
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Event_API event_api = retrofit.create(Event_API.class);
            String json = "{\n" +
                    "    \"shiftID\": " + ca + ",\n" +
                    "    \"subjectClassID\":" + lop + ",\n" +
                    "    \"dateTime\": \"" + dateTime + "\"\n" +
                    "}";
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
            event_api.Postdata(requestBody).enqueue(new Callback<Event_Post>() {
                @Override
                public void onResponse(Call<Event_Post> call, Response<Event_Post> response) {
                }

                @Override
                public void onFailure(Call<Event_Post> call, Throwable t) {

                }
            });
            verifyPermission();
            int calID = getCalendarId();
            long startmillis = 0;
            long endmillis = 0;
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startshift);
            startmillis = c1.getTimeInMillis();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endshiff);
            endmillis = c2.getTimeInMillis();
            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startmillis);
            values.put(CalendarContract.Events.DTEND, endmillis);
            values.put(CalendarContract.Events.TITLE, "Lop : N0" + lop);
            values.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY");
            values.put(CalendarContract.Events.DESCRIPTION, "Lop: N0" + lop + "\nCa :" + ca);
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cr.insert(CalendarContract.Events.CONTENT_URI, values);
            while (hientai.before(cuoicung))
            {
                beginTime.setTime(date);
                beginTime.add(Calendar.DATE,7);
                String a = format.format(beginTime.getTime());
                hientai = beginTime.getTime();
                date=beginTime.getTime();
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
//           SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd'T'");

            Date date=null;

            try {
                date =format.parse(editText_ngay.getText().toString());
                startshift=format.parse(editText_ngay.getText().toString()+"'T'"+millis_start);
                endshiff=format.parse(editText_ngay.getText().toString()+"'T'"+millis_end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar beginTime= Calendar.getInstance();
            beginTime.setTime(date);
//            startMillis=beginTime.getTimeInMillis();
            hientai=beginTime.getTime();
            beginTime.add(Calendar.YEAR,1);
//            endMillis=beginTime.getTimeInMillis();
            cuoicung=beginTime.getTime();
            dateTime=DateTime.parseRfc3339(editText_ngay.getText().toString());
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME).client(okHttpClient)
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
            verifyPermission();
            int calID = getCalendarId();
            long startmillis = 0;
            long endmillis = 0;
            Calendar c1= Calendar.getInstance();
            c1.setTime(startshift);
            startmillis=c1.getTimeInMillis();
            Calendar c2= Calendar.getInstance();
            c2.setTime(endshiff);
            endmillis=c2.getTimeInMillis();
            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART,startmillis);
            values.put(CalendarContract.Events.DTEND, endmillis);
            values.put(CalendarContract.Events.TITLE, "Lop : N0"+lop);
            values.put(CalendarContract.Events.RRULE,"FREQ=DAILY");
            values.put(CalendarContract.Events.DESCRIPTION,"Lop: N0"+lop+"\nCa :"+ca);
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
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
            hientai=c1.getTime();
            c1.add(Calendar.YEAR,1);
            cuoicung=c1.getTime();
            dateTime=DateTime.parseRfc3339(editText_ngay.getText().toString());
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.DOMAIN_NAME).client(okHttpClient)
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

    private int getCalendarId() {


//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//        }
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"hasagidzo@gmail.com", "com.google",
                "hasagidzo@gmail.com"};
        int calID = 0;
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        // Get the field values
        cur.moveToFirst();
        calID = cur.getInt(PROJECTION_ID_INDEX);
      //  String title = "Lớp : " + lop + "\n Ca :" + ca;
    //    ContentValues values = new ContentValues();
        return calID;
}

    private void get_Shift() {
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
//                Log.d("bbbb", t.getCause().getMessage());
            }
        });
    }

    private void get_Class() {
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
                arrayListClass= (ArrayList<SubjectClass>) response.body();
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
//                Log.d("aaa", t.getCause().getMessage());
            }
        });
    }
}
