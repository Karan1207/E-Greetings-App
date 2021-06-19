package com.example.KaranProjects.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.KaranProjects.Model.CategoryModel;
import com.example.KaranProjects.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter
{
    Context context;
    List<CategoryModel>list;

    public CategoryAdapter(Context context,List<CategoryModel>list)
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
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater in = LayoutInflater.from(context);
        convertView = in.inflate(R.layout.design_of_dashboard,parent,false);

        ImageView rimgview = convertView.findViewById(R.id.img);
        TextView textView = convertView.findViewById(R.id.txt);

        CategoryModel c = list.get(position);
        textView.setText(c.getName());
        Picasso.get().load(c.getImage()).placeholder(R.drawable.logo).resize(250,250).into(rimgview);

        return convertView;
    }
}
