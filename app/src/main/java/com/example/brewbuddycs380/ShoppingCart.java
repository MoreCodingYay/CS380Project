package com.example.brewbuddycs380;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity for displaying the shopping cart.
 */
public class ShoppingCart extends AppCompatActivity {

    ListView listView;
    String[] coffeeNames;
    int[] coffeeImages;
    String[] coffeeDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.list_view);

        coffeeNames = new String[UserService.shoppingCart.size()];
        coffeeImages = new int[UserService.shoppingCart.size()];
        coffeeDescriptions = new String[UserService.shoppingCart.size()];

        // Fills the arrays to put in the coffee adapter
        for (int i = 0; i < UserService.shoppingCart.size(); i++) {
            coffeeNames[i] = UserService.shoppingCart.get(i).getName();
            coffeeImages[i] = UserService.shoppingCart.get(i).getDrawableId();
            coffeeDescriptions[i] = UserService.shoppingCart.get(i).getDescription();
        }

        // Creates the coffee adapter
        CoffeeAdapter adapter = new CoffeeAdapter(this, coffeeNames, coffeeDescriptions, coffeeImages);
        // Sets the adapter for the list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Change view to fullscreen view of the text
            }
        });

        findViewById(R.id.checkout_button).setOnClickListener(l -> {
            runOnUiThread(() -> UserService.checkOut());
            Toast.makeText(getApplicationContext(), "Checked out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RecommendationScreen.class));
        });
    }
}
