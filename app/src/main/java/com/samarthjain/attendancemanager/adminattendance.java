package com.samarthjain.attendancemanager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminattendance extends AppCompatActivity {

    ListView listView;
    Spinner class_name;
    String classes;
    EditText date;
    ArrayList<String> Userlist = new ArrayList<String>();
    ArrayList<String> Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String required_date;
    //Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminattendance);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setTitle("Attendance Records");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.list);
        class_name = (Spinner) findViewById(R.id.spinner);
        date = (EditText) findViewById(R.id.date1);
        classes = class_name.getSelectedItem().toString();


    }

    public void display_list(final ArrayList<String> userlist) {
        Studentlist.clear();
        required_date = date.getText().toString();
        dbAttendance = ref.child("attendance");
        dbAttendance.child(required_date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Result will be holded Here
                if (dataSnapshot.exists()) {
                    //create new user
                    Studentlist.add("      SID         " + "p1  " + "p2  " + "p3  " + "p4   " + "p5   " + "p6  " + "p7  " + "p8");
                    String p1,p2,p3,p4,p5,p6,p7,p8;
                    for (Object sid : userlist) {

                        //DataSnapshot dsp=dataSnapshot.child(sid.toString());
                        /*String p1=dataSnapshot.child(sid.toString()).child("p1").getValue().toString().substring(0,1);
                        String p2=dataSnapshot.child(sid.toString()).child("p2").getValue().toString().substring(0,1);
                        String p3=dataSnapshot.child(sid.toString()).child("p3").getValue().toString().substring(0,1);
                        String p4=dataSnapshot.child(sid.toString()).child("p4").getValue().toString().substring(0,1);
                        String p5=dataSnapshot.child(sid.toString()).child("p5").getValue().toString().substring(0,1);
                        String p6=dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0,1);
                        String p7=dataSnapshot.child(sid.toString()).child("p7").getValue().toString().substring(0,1);
                        String p8=dataSnapshot.child(sid.toString()).child("p8").getValue().toString().substring(0,1);*/
                        if(dataSnapshot.child(sid.toString()).child("p1").getValue().toString().substring(0, 1)!="-"){
                            p1 = dataSnapshot.child(sid.toString()).child("p1").getValue().toString().substring(0, 1);

                        }else{
                            p1="A";}
                        if(dataSnapshot.child(sid.toString()).child("p2").getValue().toString().substring(0, 1)!="-"){
                            p2 = dataSnapshot.child(sid.toString()).child("p2").getValue().toString().substring(0, 1);
                        }else{
                            p2="A";}
                        if(dataSnapshot.child(sid.toString()).child("p3").getValue().toString().substring(0, 1)!="-"){
                            p3 = dataSnapshot.child(sid.toString()).child("p3").getValue().toString().substring(0, 1);
                        }else{
                            p3="A";}
                        if(dataSnapshot.child(sid.toString()).child("p4").getValue().toString().substring(0, 1)!="-"){
                            p4 = dataSnapshot.child(sid.toString()).child("p4").getValue().toString().substring(0, 1);
                        }else{
                            p4="A";}
                        if(dataSnapshot.child(sid.toString()).child("p5").getValue().toString().substring(0, 1)!="-"){
                            p5 = dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0, 1);
                        }else{
                            p5="A";}
                        if(dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0, 1)!="-"){
                            p6 = dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0, 1);
                        }else{
                            p6="A";}
                        if(dataSnapshot.child(sid.toString()).child("p7").getValue().toString().substring(0, 1)!="-"){
                            p7 = dataSnapshot.child(sid.toString()).child("p7").getValue().toString().substring(0, 1);
                        }else{
                            p7="A";}
                        if(dataSnapshot.child(sid.toString()).child("p8").getValue().toString().substring(0, 1)!="-"){
                            p8 = dataSnapshot.child(sid.toString()).child("p8").getValue().toString().substring(0, 1);
                        }else{
                            p8="A";}

                        Studentlist.add(dataSnapshot.child(sid.toString()).getKey().toString() + "         " + p1 + "     " + p2 + "     " + p3 + "     " + p4 + "      " + p5 + "       " + p6 + "      " + p7 + "      " + p8);

                        //add result into array list
                    }
                    //Toast.makeText(getApplicationContext(),Studentlist.toString(), Toast.LENGTH_LONG).show();
                    list(Studentlist);
                }else{
                    Toast.makeText(getApplicationContext(), "SELECTED DATE DATA NOT AVAILABLE", Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });




    }




    public void viewlist(View v){

        Userlist.clear();
        dbStudent = ref.child("Student");
        dbStudent.orderByChild("classes").equalTo(class_name.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        Userlist.add(dsp.child("sid").getValue().toString());
                    }
                    display_list(Userlist);
                }
                catch (Exception e)
                {
                    Toast.makeText(adminattendance.this,"exception: "+e,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });




    }
    public void list(ArrayList<String> studentlist){
        //int color = Color.argb(255, 255, 175, 64);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        listView.setAdapter(adapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

