package com.example.KaranProjects.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class register extends AppCompatActivity {

    EditText edtusername1;
    EditText edtpass1;
    EditText edtname;
    EditText edtsurname;
    Button registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtusername1=findViewById(R.id.edtusername1);
        edtpass1=findViewById(R.id.edtpass1);
        edtname=findViewById(R.id.edtname);
        edtsurname=findViewById(R.id.edtsurname);
        registerbtn=findViewById(R.id.registerbtn);


        registerbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String name=edtname.getText().toString();
                String surname=edtsurname.getText().toString();
                String username=edtusername1.getText().toString();
                String pass=edtpass1.getText().toString();

               // Toast.makeText(register.this, ""+name+surname, Toast.LENGTH_SHORT).show();


                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL.INSERT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(register.this, "Registration done", Toast.LENGTH_SHORT).show();

                        Intent i =new Intent(register.this, MainActivity.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(register.this, "No Internet", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {

                        HashMap map=new HashMap();
                        map.put("name",name);
                        map.put("surname",surname);
                        map.put("username",username);
                        map.put("password",pass);
                        return map;

                    }
                };

                RequestQueue queue= Volley.newRequestQueue(register.this);
                queue.add(stringRequest);

            }
        });

    }
}