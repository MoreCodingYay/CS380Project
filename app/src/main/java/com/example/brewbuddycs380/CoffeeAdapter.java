package com.example.brewbuddycs380;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CoffeeAdapter extends BaseAdapter {
    public CoffeeAdapter(AppCompatActivity mainActivity, String[] name, String[] description, int[] image) {
        this.mainActivity = mainActivity;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    AppCompatActivity mainActivity;
    String[] name;
    String[] description;
    int[] image;
    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mainActivity).inflate(R.layout.coffee_adapter_layout, parent, false);
        TextView nameView, descView;
        ImageView imgView;
        nameView=convertView.findViewById(R.id.nameView);
        descView=convertView.findViewById(R.id.descriptionView);
        imgView = convertView.findViewById(R.id.imageView);
        nameView.setText(name[position]);
        descView.setText(description[position]);
        imgView.setImageResource(image[position]);
        return convertView;
    }
}
