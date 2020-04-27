package com.example.attendance.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Add_Lesson;
import com.example.attendance.DataShop;
import com.example.attendance.R;
import com.example.attendance.ShopAdapter;
import com.example.attendance.Student;
import com.example.attendance.monthCalendar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private CalendarView cld;
    Button btn_themlichday;
    public static ArrayList<Student> list_student_diemdanh;
    public static ArrayList<Student> list_student_chuadiemdanh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = root.findViewById(R.id.listTB);
        cld = root.findViewById(R.id.Cld);
        // Bắt đầu đưa dữ liệu vào
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setItemAnimator;
        btn_themlichday=(Button)root.findViewById(R.id.button_themlich);
        btn_themlichday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_Lesson.class);
                startActivity(intent);
            }
        });

        cld.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//                Toast.makeText(getActivity(), ""+i2, Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(null);

                if(i2 == 15){
                    ArrayList<DataShop> arrayList = new ArrayList<>();
                    arrayList.add(new DataShop("09:30AM","12AM","103-A2","ATBMTT"));
                    arrayList.add(new DataShop("13 PM","15:30 PM","102-A2","Web"));
                    arrayList.add(new DataShop("15:30 PM","18:00 PM","102-A2","LTM"));
                    ShopAdapter shopAdapter = new ShopAdapter(arrayList,getActivity());
                    recyclerView.setAdapter(shopAdapter);
                }
                if(i2 == 13){
                    ArrayList<DataShop> arrayList = new ArrayList<>();
                    arrayList.add(new DataShop("sk ngày 13","sk ngày 13","sk ngày 13","sk ngày 15"));
                    ShopAdapter shopAdapter = new ShopAdapter(arrayList,getActivity());
                    recyclerView.setAdapter(shopAdapter);
                }
                if(i2 == 20){
                    ArrayList<DataShop> arrayList = new ArrayList<>();
                    arrayList.add(new DataShop("sk ngày 20","sk ngày 20","sk ngày 20","sk ngày 15"));
                    ShopAdapter shopAdapter = new ShopAdapter(arrayList,getActivity());
                    recyclerView.setAdapter(shopAdapter);
                }
            }
        });




        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);

            }
        });
        return root;
    }
}