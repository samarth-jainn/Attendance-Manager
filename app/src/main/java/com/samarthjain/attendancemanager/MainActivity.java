package com.samarthjain.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;
import com.samarthjain.attendancemanager.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public ImageView logo;
    public  static int splashTimeout=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=(ImageView)findViewById(R.id.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
                finish();
            }
        },splashTimeout);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanim);
        logo.startAnimation(myanim);


    }
}
