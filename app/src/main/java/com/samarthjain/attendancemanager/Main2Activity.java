package com.samarthjain.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Main2Activity extends AppCompatActivity {
    private static long back_pressed;
    Button admin, student, teacher,info;

   /* public void stu_login(View view) {
        Intent i = new Intent(Main2Activity.this, Student_login.class);
        startActivity(i);


    }

    public void tea_login(View view) {
        Intent i = new Intent(Main2Activity.this, Teacher_login.class);
        startActivity(i);


    }

    public void admin_pg(View view) {
        Intent i = new Intent(Main2Activity.this, Admin.class);
        startActivity(i);


    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        admin = findViewById(R.id.viewat);
        student = findViewById(R.id.record);
        teacher = findViewById(R.id.buttonTeacher);
        info= findViewById(R.id.buttonInfo);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,Admin.class);
                startActivity(intent);
                finish();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Student_login.class);
                startActivity(intent);
                finish();
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Teacher_login.class);
                startActivity(intent);
                finish();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "SELECT ONE OF THE ABOVE LOGIN FEATURE", Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
