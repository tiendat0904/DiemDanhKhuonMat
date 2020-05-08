package com.example.attendance.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Activity.Add_Lesson;
import com.example.attendance.Model.Event_Details;
import com.example.attendance.R;
import com.example.attendance.Adapter.ShopAdapter;
import com.example.attendance.Model.Student;
import com.example.attendance.ui.Other.UnsafeOkHttpClient;
import com.example.attendance.API.Event_details;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private CalendarView cld;
    Button btn_themlichday;
    ArrayList<String> list_event_details;
    TextView t;
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
       // t = root.findViewById(R.id.test);
        // Bắt đầu đưa dữ liệu vào
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.divider_custom);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getDate();
        btn_themlichday=(Button)root.findViewById(R.id.button_themlich);
        btn_themlichday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_Lesson.class);
                startActivity(intent);

            }
        });
        return root;
    }

    private void getDate() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:64535/api/Events/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        list_event_details=new ArrayList<>();
        Event_details class_Service =retrofit.create(Event_details.class);
        Call<List<Event_Details>> call = class_Service.getEvent_Details();
        call.enqueue(new Callback<List<Event_Details>>() {
            @Override
            public void onResponse(Call<List<Event_Details>> call, Response<List<Event_Details>> response) {
                if(!response.isSuccessful()){
                    try {
                        Log.d("aaa", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                List<Event_Details> event_details = response.body();
                cld.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                        recyclerView.setAdapter(null);

                        ArrayList<Event_Details> event_details1 = new ArrayList<>();
                        for(int j=0;j<event_details.size();j++)
                        {
                            String[] str = event_details.get(j).getDateTime().split("\\-");
                            if( i2==Integer.parseInt(str[0])  && i1==(Integer.parseInt(str[1]) -1) &&   i==Integer.parseInt(str[2]))
                            {


                                event_details1.add(new Event_Details(event_details.get(j).getSubjectClassName(),event_details.get(j).getDateTime(),event_details.get(j).getShiftName(),event_details.get(j).getEventID()));

                            }
                        }
                        ShopAdapter shopAdapter = new ShopAdapter(event_details1,getActivity());
                        recyclerView.setAdapter(shopAdapter);


                    }
                });
            }

            @Override
            public void onFailure(Call<List<Event_Details>> call, Throwable t) {
       //         Log.d("aaa", t.getCause().getMessage());
            }
        });

    }
}