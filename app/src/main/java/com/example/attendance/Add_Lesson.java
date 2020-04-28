package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Add_Lesson extends AppCompatActivity {
    EditText editText_ngay;
    Button btn_chonlich;
    Spinner spinner_luachon,spinner_lop;
    ArrayList<String> list_luachon;
    ArrayList<SubjectClass> list_class = new ArrayList<>();
    SimpleDateFormat fmtDateAndTime = new SimpleDateFormat("dd/MM/yyyy");
    ArrayAdapter<String> arrayAdapter1;
    ArrayList<String> spinner_class1 = new ArrayList<>();
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
        spinner_lop = (Spinner)findViewById(R.id.spinner_lop);
        list_luachon = new ArrayList<>();
        list_luachon.add("");
        list_luachon.add("Hàng Ngày");
        list_luachon.add("Hàng Tuần");
        list_luachon.add("Tất cả ngày trong tuần");
        list_luachon.add("Tùy Chỉnh");
        loadData();
        arrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinner_class1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_lop.setAdapter(arrayAdapter1);
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
//    class docJSON extends AsyncTask<String,Integer,String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//    }
//
//    private static String docnoiDung_tu_URL(String theurl)
//    {
//        StringBuilder content = new StringBuilder();
//        try
//        {
//            URL url  = new URL(theurl);
//
//        }
//    }

    //no bao la loi ket noi, k ket noi den dc cai duong dan dayvi no k co san
        private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = "10.0.2.2:44315/api/SubjectClasses";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, duongdan, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Log.d("zxzxzxzx", response);
                int subjectClassID;
                String subjectClassName;
                int status;
                int subjectID;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        subjectClassID = jsonObject.getInt("subjectClassID");
                        subjectClassName = jsonObject.getString("subjectClassName");
                        status = jsonObject.getInt("status");
                        subjectID = jsonObject.getInt("subjectID");
                        spinner_class1.add(subjectClassName);
                        list_class.add(new SubjectClass(subjectClassID,subjectClassName,status,subjectID));
                    }
                    arrayAdapter1.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d("zxzxzxzx", e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("zxzxzxzx", error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}
