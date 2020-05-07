package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class CustomRepeat extends AppCompatActivity {
    TextView txtsoluong;
    EditText editTextsolanlap;
    Chip T2,T3,T4,T5,T6,T7,CN;
    RadioButton rdAppear;
    String txtT2,txtT3,txtT4,txtT5,txtT6,txtT7,txtCN,kieulap;
    Button btn_cong,btn_tru,btnDone,btnCancel;
    Toolbar toolbar;
    int dem=1;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_repeat);
        txtsoluong = (TextView)findViewById(R.id.textView_amount);
        btn_cong = (Button)findViewById(R.id.button_cong);
        btn_tru = (Button)findViewById(R.id.button_tru);
        T2=(Chip)findViewById(R.id.T2);
        T3=(Chip)findViewById(R.id.T3);
        T4=(Chip)findViewById(R.id.T4);
        T5=(Chip)findViewById(R.id.T5);
        T6=(Chip)findViewById(R.id.T6);
        T7=(Chip)findViewById(R.id.T7);
        CN=(Chip)findViewById(R.id.CN);
        rdAppear=(RadioButton)findViewById(R.id.radioButton_appear);
//        rdNever=(RadioButton)findViewById(R.id.radioButton_kbh);
        btnDone=(Button)findViewById(R.id.button_done);
        btnCancel=(Button)findViewById(R.id.button_cancel);
        editTextsolanlap=(EditText)findViewById(R.id.editText_solanlap);
        getData();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("T2",txtT2);
                intent.putExtra("T3",txtT3);
                intent.putExtra("T4",txtT4);
                intent.putExtra("T5",txtT5);
                intent.putExtra("T6",txtT6);
                intent.putExtra("T7",txtT7);
                intent.putExtra("CN",txtCN);
                intent.putExtra("laptuan",txtsoluong.getText());
                intent.putExtra("kieulap",kieulap);
               setResult(RESULT_OK,intent);
               finish();
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar_custom_repeat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtsoluong.setText(dem+"");
        btn_tru.setVisibility(View.INVISIBLE);

        btn_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dem++;
                if(dem==100000)
                {
                    txtsoluong.setText(dem+"");
                    btn_cong.setVisibility(View.INVISIBLE);
                }
                else{
                    btn_tru.setVisibility(View.VISIBLE);
                    txtsoluong.setText(dem+"");
                }

            }
        });
        btn_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dem--;
                if(dem==1)
                {
                    txtsoluong.setText(dem+"");
                    btn_tru.setVisibility(View.INVISIBLE);
                }
                else{
                    btn_cong.setVisibility(View.VISIBLE);

                    txtsoluong.setText(dem+"");
                }
            }
        });
    }

    private void getData() {
        T2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    txtT2 = 2+"";
                } else {
                    txtT2 = "";
                }
            }
        });
        T3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    txtT3 = 3+"";
                } else {
                    txtT3 = "";
                }
            }
        });
        T4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    txtT4=4+"";
                }
                else {
                    txtT4="";
                }
            }
        });
        T5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    txtT5=5+"";
                }
                else {
                    txtT5="";
                }
            }
        });
        T6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    txtT6=6+"";
                }
                else {
                    txtT6="";
                }
            }
        });
        T7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    txtT7=7+"";
                }
                else {
                    txtT7="";
                }
            }
        });
        CN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    txtCN=1+"";
                }
                else {
                    txtCN="";
                }
            }
        });
        rdAppear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    kieulap=editTextsolanlap.getText().toString();
                }
            }
        });
    }
}
