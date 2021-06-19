
package com.example.KaranProjects.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.KaranProjects.Adapter.BirthdayAdapter;
import com.example.KaranProjects.BuildConfig;
import com.example.KaranProjects.Model.BirthdayModel;
import com.example.KaranProjects.Model.CategoryModel;
import com.example.KaranProjects.R;
import com.example.KaranProjects.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Birthday extends AppCompatActivity
{
    GridView grid;
    List<BirthdayModel>list;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grid = findViewById(R.id.gridview);
        list = new ArrayList<>();
        

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.BIRTHDAY_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String i1 = jsonObject.getString("images");
                        BirthdayModel birthdayModel = new BirthdayModel();
                        birthdayModel.setImage(i1);
                        list.add(birthdayModel);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                BaseAdapter birthdayAdapter = new BirthdayAdapter(Birthday.this, list);
                grid.setAdapter(birthdayAdapter);

                grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (position == 0)
                        {
                            Intent i = new Intent(Birthday.this,FullScreenImage.class);
                            i.putExtra("birthday",position);
                            startActivity(i);
                            Toast.makeText(Birthday.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                        if (position == 1)
                        {
                            Toast.makeText(Birthday.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                        if (position == 2)
                        {
                            Toast.makeText(Birthday.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                        if (position == 3)
                        {
                            Toast.makeText(Birthday.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        },

                new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Birthday.this, "No INTERNET!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(Birthday.this);
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

                case R.id.logout:
                sharedPreferences.edit().clear().commit();
                Intent i = new Intent(Birthday.this,MainActivity.class);
                startActivity(i);
                finish();

                case R.id.share:
                Intent shareIntentApp = new Intent(Intent.ACTION_SEND);
                shareIntentApp.setType("text/plain");
                shareIntentApp.putExtra(Intent.EXTRA_SUBJECT,"E-Greetings");
                String shareMessageApp = "\n Let me recommend you this Wonderfull Application\n\n";
                shareMessageApp = shareMessageApp + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntentApp.putExtra(Intent.EXTRA_TEXT, shareMessageApp);
                startActivity(Intent.createChooser(shareIntentApp, "choose one"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}