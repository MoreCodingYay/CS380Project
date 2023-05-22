package com.example.brewbuddycs380;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class reccomendationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reccomendation_screen);
        TextView recommendation = findViewById(R.id.recommendation);

        String topChoice = CoffeeRecommender.recommendTopCoffee(questionSubmit.getPreferences()).getName();
        String preferences = CoffeeRecommender.getPropertiesString(questionSubmit.getPreferences());
        recommendation.setText(topChoice + " " + preferences);
    }
}