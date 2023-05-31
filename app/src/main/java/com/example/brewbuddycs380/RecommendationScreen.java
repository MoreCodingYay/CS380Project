package com.example.brewbuddycs380;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.EnumSet;

public class RecommendationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reccomendation_screen);
        TextView recommendation = findViewById(R.id.recommendation);
        TextView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.coffee);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setRating(4.5F);
        // gets data passed to it from the questionSubmit class
        // this is selected preferences in the form of an array
        Properties[] selectedPropertiesArray = (Properties[]) getIntent().getSerializableExtra("selectedProperties");
        // same preferences, in the form of an enumSet
        EnumSet<Properties> userPreferences = EnumSet.copyOf(Arrays.asList(selectedPropertiesArray));


        Coffee topChoice = CoffeeRecommender.recommendTopCoffee(userPreferences);
        String bodyDescription = topChoice.getDescription();
        String preferences = CoffeeRecommender.getPreferencesString(userPreferences);


        recommendation.setText(topChoice.getName() + " with " + preferences );
        description.setText(topChoice.getDescription());
        image.setImageResource(topChoice.getDrawableId());

    }
}