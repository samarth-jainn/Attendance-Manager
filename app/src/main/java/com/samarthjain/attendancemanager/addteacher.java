package com.samarthjain.attendancemanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samarthjain.attendancemanager.models.teachers;

public class addteacher extends AppCompatActivity {

    private static final String TAG ="" ;
    EditText Tname;
    EditText Tid;
    EditText subject,tpassword;
    String tname,tid,sub,classname,tpass;
    Spinner classes;
    Button addButton;
    DatabaseReference databaseTeacher;
    //Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);
        databaseTeacher = FirebaseDatabase.getInstance().getReference("Teacher");

        Tname =  (EditText) findViewById(R.id.teaname);
        Tid =  (EditText) findViewById(R.id.teaid);
        subject =  (EditText) findViewById(R.id.subject);
        classes = (Spinner) findViewById(R.id.spinner4);
        tpassword =  (EditText) findViewById(R.id.teapass);
    }
    public void addte(View v){
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        sub = subject.getText().toString();
        classname = classes.getSelectedItem().toString();
        tpass = tpassword.getText().toString();


        databaseTeacher.orderByChild("tid").equalTo(tid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        //create new user
                        Toast.makeText(getApplicationContext(), "USER ALREADY EXIXTS",Toast.LENGTH_LONG).show();


                    }else{
                        if (!(TextUtils.isEmpty(Tid.getText().toString()))) {
                            // String id = databaseTeacher.push().getKey();
                            teachers teacher = new teachers(tname, tid, sub, classname, tpass);
                            databaseTeacher.child(tid).setValue(teacher);
                            Toast.makeText(getApplicationContext(), "Teacher added successfully", Toast.LENGTH_LONG).show();
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_LONG).show();
                        }
                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!


            }
        });






    }
    public void removete(View v){
        if (!TextUtils.isEmpty(Tid.getText().toString())) {
            tid = Tid.getText().toString();
            databaseTeacher.child(tid).setValue(null);
            Toast.makeText(getApplicationContext(),"Teacher removed successfully", Toast.LENGTH_LONG).show();
            finish();

        }else {
            Toast.makeText(getApplicationContext(),"id cannot be empty", Toast.LENGTH_LONG).show();
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
