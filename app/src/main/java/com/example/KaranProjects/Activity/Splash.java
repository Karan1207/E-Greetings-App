package com.example.KaranProjects.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.KaranProjects.R;

public class Splash extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.welcometxt);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {



                YoYo.with(Techniques.Tada)
                        .duration(800)
                        .repeat(1)
                        .playOn(textView);


                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);





            }
        },3000);
    }
}