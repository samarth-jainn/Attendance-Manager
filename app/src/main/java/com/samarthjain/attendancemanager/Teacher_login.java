package com.samarthjain.attendancemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Teacher_login extends AppCompatActivity {

    Button login;
    DatabaseReference ref;
    String userid,pass;
    String dbpassword;
    Bundle basket;
    EditText username,password;
    ProgressDialog mDialog;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        login = findViewById(R.id.login_bt_tea);
        username = findViewById(R.id.usernameteacher);
        password = findViewById(R.id.passwordteacher);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = username.getText().toString();
                pass = password.getText().toString();
                mDialog=new ProgressDialog(Teacher_login.this);
                mDialog.setMessage("Please Wait..."+userid);
                mDialog.setTitle("Loading");
                mDialog.show();

                basket = new Bundle();
                basket.putString("message", userid);

                ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dbuser = ref.child("Teacher").child(userid);


                dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDialog.dismiss();
                        dbpassword = dataSnapshot.child("tpass").getValue(String.class);
                        verify(dbpassword);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "database error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void verify(String dbpassword) {
        if(userid.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (pass.equalsIgnoreCase(this.dbpassword) ) {
            //  if (userid.equalsIgnoreCase("admin") && pass.equals("admin")) {
            mDialog.dismiss();
            Intent intent = new Intent(Teacher_login.this, Teacher_features.class);
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



