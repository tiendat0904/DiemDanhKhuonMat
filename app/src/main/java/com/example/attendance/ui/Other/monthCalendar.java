package com.example.attendance.ui.Other;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.attendance.R;

import static android.app.Activity.RESULT_OK;

public class monthCalendar extends Fragment {

    //private GalleryViewModel galleryViewModel;
    static final int REQUEST_ID_IMAGE_CAPTURE = 100;

    Button btn;
    ImageView anh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.activity_month_calendar, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        btn = root.findViewById(R.id.button_permission_AI);
        anh = root.findViewById(R.id.image);
        //galleryViewModel.getText().observe(this, new Observer<String>() {
        //@Override
        //public void onChanged(@Nullable String s) {
        //   textView.setText(s);
        //}
        //});

        //final String[] permissions = {Manifest.permission.CAMERA};
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermission();
            }
        });
        return root;

    }
    private void verifyPermission()
    {
        final int permission_camera = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA);
        if(permission_camera != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA))
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_ID_IMAGE_CAPTURE);
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_ID_IMAGE_CAPTURE);
            }
        }
        else {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent1, REQUEST_ID_IMAGE_CAPTURE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            anh.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_ID_IMAGE_CAPTURE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length>0){
                    verifyPermission();
                }else{
                    Toast.makeText(getContext(),"Permission denied....",Toast.LENGTH_SHORT).show();
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
