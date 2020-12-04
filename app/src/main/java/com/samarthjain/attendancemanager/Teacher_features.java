package com.samarthjain.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.AdapterView.*;

public class Teacher_features extends AppCompatActivity {


    String item;
    String message;
    //Toolbar mToolbar;
    private static long back_pressed;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
   // private View view;
   // private long id;

    public void logouteach(View view) {
        Intent i= new Intent(Teacher_features.this,Teacher_login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }
    public void addstu1(View v){
        Intent intent = new Intent(this, addstudent.class);
        startActivity(intent);
    }
    public void attenrec1(View v){
        Intent intent = new Intent(Teacher_features.this, adminattendance.class);
        startActivity(intent);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_features);
        Spinner spinnerr = (Spinner) findViewById(R.id.spinner4);


        //to get username from login page
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");
        //mToolbar=(Toolbar)findViewById(R.id.takeattendancebar);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setTitle(message+"'s Dashboard  - "+date);

        TextView txtView = (TextView) findViewById(R.id.textView3);
        txtView.setText("Welcome : "+message);
        //spinnerr.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        List<String> categories = new ArrayList<String>();
        categories.add("CSE");
        categories.add("ME");
        categories.add("CE");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerr.setAdapter(dataAdapter);
        spinnerr.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }





    public void takeatten(View v){
        Bundle basket= new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);

        Intent intent = new Intent(Teacher_features.this, takeattendance.class);
        intent.putExtras(basket);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
