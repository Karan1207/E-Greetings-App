package com.example.KaranProjects.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.KaranProjects.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

public class FullScreenImage extends AppCompatActivity
{



    PhotoView photoView;
    Button download,share;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);


        photoView = findViewById(R.id.photo);
        download = findViewById(R.id.downloadbtn);
        share = findViewById(R.id.sharebtn);

        Intent i = getIntent();
        image = i.getStringExtra("birthday");

        Picasso.get().load(image).into(photoView);

        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                download();
            }
        });

        share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sharedata();
            }
        });

    }

    private void sharedata()
    {
        Picasso.get().load(image).into(new Target()
        {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
            {

                Intent i =new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_STREAM,getImageUri(getApplicationContext(),bitmap));
                i.putExtra(Intent.EXTRA_TEXT,"Share My App");
                i.setType("image/*");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(i,"app"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable)
            {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable)
            {
            }
        });
    }

    private static Uri getImageUri(Context applicationContext, Bitmap bitmap)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), bitmap, "E-Greetings", null);
        return  Uri.parse(path);
    }

    private void download()
    {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(image);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Download Started", Toast.LENGTH_SHORT).show();
    }
}