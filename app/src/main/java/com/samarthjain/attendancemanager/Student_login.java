package com.samarthjain.attendancemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_login extends AppCompatActivity {

    Button login;
    DatabaseReference ref;
    String userid,pass;
    String dbpassword;
    Bundle basket;
    EditText username,password;
    ProgressDialog mDialog;
    Spinner classsel;
    private static long back_pressed;
    String class_sel;
    String class_sele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        login = findViewById(R.id.login_bt_stu);
        username = findViewById(R.id.username_stu);
        classsel=(Spinner)findViewById(R.id.classsel);
        password = findViewById(R.id.password_stu);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = username.getText().toString();
                pass = password.getText().toString();
                class_sel=classsel.getSelectedItem().toString();
                mDialog = new ProgressDialog(Student_login.this);
                mDialog.setMessage("Please Wait..." + userid);
                mDialog.setTitle("Loading");
                mDialog.show();
                basket = new Bundle();
                basket.putString("message", userid);
                basket.putString("cls",class_sel);


                ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dbuser = ref.child("Student").child(userid);
                dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDialog.dismiss();
                        dbpassword = dataSnapshot.child("spass").getValue(String.class);
                        class_sele= dataSnapshot.child("classes").getValue(String.class);

                        verify(dbpassword,class_sele);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "database error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public void verify(String dbpassword,String class_sele) {
        if(userid.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (pass.equalsIgnoreCase(dbpassword) && class_sel.equalsIgnoreCase(class_sele) ) {
            //  if (userid.equalsIgnoreCase("admin") && pass.equals("admin")) {
            mDialog.dismiss();
            Intent intent = new Intent(this, Student_features.class);
            intent.putExtras(basket);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
            finish();
            //  }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Enter valid user id or password", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            Intent i =new Intent(this,Main2Activity.class);
            startActivity(i);
            finish();
            //ActivityCompat.finishAffinity(this);
            //System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to go back to login menu", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
    }











