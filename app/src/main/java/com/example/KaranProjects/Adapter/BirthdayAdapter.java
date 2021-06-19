package com.example.KaranProjects.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.KaranProjects.Activity.FullScreenImage;
import com.example.KaranProjects.Model.BirthdayModel;
import com.example.KaranProjects.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BirthdayAdapter extends BaseAdapter
{
    Context context;
    List<BirthdayModel>list;

    public BirthdayAdapter(Context context , List<BirthdayModel>list)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.design_birthday,parent,false);
        ImageView img = convertView.findViewById(R.id.img);
        BirthdayModel birthdayModel = list.get(position);
        /*Picasso.get().load(birthdayModel.getImage())*//*.resize(500,500)*//*.into(img);*/
        Picasso.get().load(birthdayModel.getImage()).placeholder(R.drawable.logo).resize(500,500).into(img);

        img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, FullScreenImage.class);
                i.putExtra("birthdayimg",list.get(position).getImage());
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
