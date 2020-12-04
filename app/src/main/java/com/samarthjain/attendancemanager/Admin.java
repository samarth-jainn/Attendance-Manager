package com.samarthjain.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {


    private static long back_pressed;
    private FirebaseAuth mAuth;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mEmailView = (EditText) findViewById(R.id.username1);
        mPasswordView = (EditText) findViewById(R.id.password1);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login_bt_stu || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }
    public void login_admin(View v)   {

        attemptLogin();

    }
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.isEmpty())
            if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Attendance Manager", "signInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d("Attendance Manager", "Problem signing in: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                } else {
                    Intent intent = new Intent(Admin.this, Admin_features.class);

                    startActivity(intent);
                }

            }
        });


    }
    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
