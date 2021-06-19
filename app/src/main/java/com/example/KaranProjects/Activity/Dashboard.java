package com.example.KaranProjects.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.KaranProjects.Adapter.CategoryAdapter;
import com.example.KaranProjects.BuildConfig;
import com.example.KaranProjects.Model.CategoryModel;
import com.example.KaranProjects.R;
import com.example.KaranProjects.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity
{

    SharedPreferences sharedPreferences;
    GridView gridView;
    List<CategoryModel>list;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
        Toast.makeText(this, "Welcome  "+sharedPreferences.getString("username","xyz"), Toast.LENGTH_SHORT).show();

        gridView = findViewById(R.id.grid);
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.CATEGORY, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("c_name");
                        String image = jsonObject.getString("c_image");

                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setName(name);
                        categoryModel.setImage(image);
                        list.add(categoryModel);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                CategoryAdapter categoryAdapter = new CategoryAdapter(Dashboard.this, list);
                gridView.setAdapter(categoryAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (position == 0)
                        {
                            Intent i = new Intent(Dashboard.this,Birthday.class);
                            startActivity(i);
                            Toast.makeText(Dashboard.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                        if (position == 1)
                        {
                            Toast.makeText(Dashboard.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                        if (position == 2)
                        {
                            Toast.makeText(Dashboard.this, "" + position, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
                , new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Dashboard.this, "No INTERNET!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(Dashboard.this);
        queue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(Dashboard.this);
        alert.setTitle("Are you sure you want to EXIT?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finishAffinity();
                /*sharedPreferences.edit().clear().commit();*/
                /*Intent i = new Intent(Dashboard.this,MainActivity.class);
                startActivity(i);
                finish();*/
                /*Intent i = new Intent(Dashboard.this,MainActivity.class);
                startActivity(i);*/
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.logout:
                sharedPreferences.edit().clear().commit();
                Intent i = new Intent(Dashboard.this,MainActivity.class);
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