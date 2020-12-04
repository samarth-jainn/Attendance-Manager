package com.samarthjain.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Student_features extends AppCompatActivity {
    String message;
    String clss;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teacher");
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_features);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        clss =bundle.getString("cls");

        //mToolbar=(Toolbar)findViewById(R.id.ftoolbar);
        //mToolbar.setTitle(message+"'s Dashboard"+"("+date+")");
        TextView txtView = (TextView) findViewById(R.id.welcome);
        txtView.setText("Welcome :"+message);

    }
    public void viewAttendance(View v){
        Bundle basket = new Bundle();
        basket.putString("sid", message);
        basket.putString("clssel",clss);
        Intent intent = new Intent(this, studentattendancerecord.class);
        intent.putExtras(basket);
        startActivity(intent);
    }



    public void logoutstu(View view) {
        Intent i= new Intent(Student_features.this,Student_login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

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
