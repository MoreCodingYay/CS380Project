package com.example.brewbuddycs380;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ShoppingCart extends AppCompatActivity {

    ListView listView;
    String[] coffeeNames;
    int[] coffeeImages;
    String[] coffeeDescriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView= findViewById(R.id.list_view);


        coffeeNames = new String[UserService.shoppingCart.size()];
        coffeeImages = new int[UserService.shoppingCart.size()];
        coffeeDescriptions = new String[UserService.shoppingCart.size()];


        for(int i = 0; i<UserService.shoppingCart.size();i++){
            coffeeNames[i]=UserService.shoppingCart.get(i).getName();
            coffeeImages[i]=UserService.shoppingCart.get(i).getDrawableId();
            coffeeDescriptions[i]=UserService.shoppingCart.get(i).getDescription();
        }
        CoffeeAdapter adapter = new CoffeeAdapter(this, coffeeNames, coffeeDescriptions, coffeeImages);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //change view to fullscreen view of the text
            }
        });
    }
}