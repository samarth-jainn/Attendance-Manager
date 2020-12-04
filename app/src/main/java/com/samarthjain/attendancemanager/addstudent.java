package com.samarthjain.attendancemanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samarthjain.attendancemanager.models.students;

public class addstudent extends AppCompatActivity {
    private static final String TAG ="" ;
    EditText Sname;
    EditText Sid,spassword;
    String sname,sid,classname,spass;
    Spinner classes;
    DatabaseReference databaseStudent;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);

        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        Sname =  (EditText) findViewById(R.id.stuname);
        Sid =  (EditText) findViewById(R.id.stuid);
        classes = (Spinner) findViewById(R.id.spinner);
        spassword = (EditText) findViewById(R.id.passstu);
        //mToolbar=(Toolbar)findViewById(R.id.ftoolbar);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setTitle("Add/Remove Student");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void addstudent1(View v){
        sname = Sname.getText().toString();
        sid = Sid.getText().toString();
        classname = classes.getSelectedItem().toString();
        spass = spassword.getText().toString();

        databaseStudent.orderByChild("sid").equalTo(sid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    //create new user
                    Toast.makeText(getApplicationContext(), "USER ALREADY EXISTS", Toast.LENGTH_LONG).show();
                }else {

                    if (!(TextUtils.isEmpty(Sid.getText().toString()))) {
                        //String id = databaseStudent.push().getKey();



                        students student =new students(sname ,sid,classname,spass );
                        databaseStudent.child(sid).setValue(student);
                        Toast.makeText(getApplicationContext(),"student added successfully", Toast.LENGTH_LONG).show();
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),"fields cannot be empty", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        });

    }
    public void removestudent(View v){
        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString();
            databaseStudent.child(sid).setValue(null);
            Toast.makeText(getApplicationContext(),"student removed successfully", Toast.LENGTH_LONG).show();
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
