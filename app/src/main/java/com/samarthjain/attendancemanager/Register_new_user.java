package com.samarthjain.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_new_user extends AppCompatActivity {

    private EditText usern;
    private EditText passwrd;
    private EditText passwrd1;

    private FirebaseAuth mauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        usern=(EditText) findViewById(R.id.username);
        passwrd=(EditText) findViewById(R.id.pass1);
        passwrd1=(EditText) findViewById(R.id.pass2);


        usern.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.id.register_form_finished || actionId == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return  false;
            }
        });

        mauth=FirebaseAuth.getInstance();

    }

    public  void signup(View view ) {
        attemptRegistration();
    }
    private void attemptRegistration() {

        usern.setError(null);
        passwrd.setError(null);

        String email = usern.getText().toString();
        String pass = passwrd.getText().toString();

        boolean cancel= false;
         View focusView = null;

         if(!TextUtils.isEmpty(pass) ) {
             passwrd.setError("password too short or doest not match");
             focusView = passwrd;
             cancel = true;
         }

         if(TextUtils.isEmpty(email)) {
             usern.setError("This Email is invalid");
             focusView = usern;
             cancel = true;
         }
        if(cancel) {
            focusView.requestFocus();
        } else {
            createFirebaseUser();
        }


    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private void isPasswordValid(String pass) {
        //TODO: Add own logic to check for a valid password

//        String confirmPassword = passwrd1.getText().toString();
//        if(confirmPassword.equals(pass) && pass.length() > 8) {
//            Pattern pattern;
//            Matcher matcher;
//            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//            pattern = Pattern.compile(PASSWORD_PATTERN);
//            matcher = pattern.matcher(pass);
//
//            return matcher.matches();
        }// return confirmPassword.equals(pass) && pass.length() > 8;


    private void createFirebaseUser() {
        String email=usern.getText().toString();
        String password = passwrd.getText().toString();


        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AttendanceManager","createUser oncomplete: " + task.isSuccessful());

                        if(!task.isSuccessful()) {
                            Log.d("Attendance Manager","user creatiuon failed");
                            showErrorDialog("Registration attempt failed");
                        } else {
                            //saveDisplayName();
                            Intent i = new Intent(Register_new_user.this,Admin_features.class);
                            finish();
                            startActivity(i);
                        }
                    }
                });
    }
   /* private void saveDisplayName() {
        String displayName = usern.getText().toString();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }*/

    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}



