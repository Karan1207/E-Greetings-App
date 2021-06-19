package com.example.KaranProjects.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.KaranProjects.R;
import com.example.KaranProjects.URL;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    EditText edtusername;
    EditText edtpass;
    Button loginbtn;
    TextView txtregister;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
        edtusername=findViewById(R.id.edtusername);
        edtpass=findViewById(R.id.edtpass);
        loginbtn=findViewById(R.id.loginbtn);
        txtregister=findViewById(R.id.txtregister);

        if(sharedPreferences.getBoolean("session",false)&&!sharedPreferences.getString("username","").isEmpty())
        {
            Intent i =new Intent(MainActivity.this,Dashboard.class);
            startActivity(i);
        }

        loginbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String username=edtusername.getText().toString();
                String pass=edtpass.getText().toString();

                if (username.equals("karan") && pass.equals("karan"))
                {
                    Toast.makeText(MainActivity.this, "It's admin", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    StringRequest stringRequest=new StringRequest(Request.Method.POST, URL.LOGIN, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if(response.trim().equals("0"))
                            {
                                Toast.makeText(MainActivity.this, "Login Failed..... Please Enter Valid Details", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                sharedPreferences.edit().putString("username",edtusername.getText().toString()).commit();
                                sharedPreferences.edit().putBoolean("session",true).commit();
                                Intent i = new Intent(MainActivity.this,Dashboard.class);
                                startActivity(i);
                                Toast.makeText(MainActivity.this, "login done", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            HashMap map=new HashMap();
                            map.put("u1",username);
                            map.put("p1",pass);
                            return map;
                        }
                    };

                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(stringRequest);


                }







            }
        });


        txtregister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Do you want to EXIT?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}