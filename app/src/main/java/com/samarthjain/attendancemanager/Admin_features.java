package com.samarthjain.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin_features extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference dbStudent;
    DatabaseReference dbAttendance;
    DatabaseReference dbadmin;
    Toolbar mToolbar;

    private static long back_pressed;
    ArrayList Studentlist = new ArrayList<>();

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    public void createuser(View view) {
        Intent i = new Intent(Admin_features.this,Register_new_user.class);
        startActivity(i);
    }
    public void addstu(View v){
        Intent intent = new Intent(this, addstudent.class);
        startActivity(intent);
    }
    public void addteach(View v){
        Intent intent = new Intent(this, addteacher.class);
        startActivity(intent);
    }
    public void logoutadmin(View view) {

        Intent logout=new Intent(Admin_features.this,Admin.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);

    }
    public void attenrec(View v){
        Intent intent = new Intent(Admin_features.this, adminattendance.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_features);


        //mToolbar.setTitle("Admin Dashboard : "+"("+date+")");
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student");
        dbAttendance = ref.child("attendance");}
        public void CreateAttendance(View view){

            //Toast.makeText(getApplicationContext(),date, Toast.LENGTH_LONG).show();




            dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String sid,P1="-",P2="-",P3="-",P4="-",P5="-",P6="-",P7="-",P8="-";
                    Attendance_sheet a = new Attendance_sheet(P1,P2,P3,P4,P5,P6,P7,P8);

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        sid=dsp.child("sid").getValue().toString();
                        dbAttendance.child(date).child(sid).setValue(a);

                    }
                    Toast.makeText(getApplicationContext(),"successfully created "+date+" db", Toast.LENGTH_LONG).show();
                }



                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something wrong", Toast.LENGTH_LONG).show();
                }

            });


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
