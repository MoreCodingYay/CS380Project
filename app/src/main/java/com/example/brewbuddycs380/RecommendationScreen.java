package com.example.brewbuddycs380;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;


public class RecommendationScreen extends AppCompatActivity {

    private Coffee lastRecommendation;

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
        //bug where everything static just does not exist
        EnumSet<Properties> userPreferences = (EnumSet<Properties>) CoffeeRecommender.stringToUserPreference(UserService.getPrefs());


        Coffee topChoice = CoffeeRecommender.recommendTopCoffee(userPreferences);
        Coffee[] top5Choices = CoffeeRecommender.recommend5Coffee(userPreferences);
        String preferences = CoffeeRecommender.getPreferencesString(userPreferences);


        recommendation.setText(topChoice.getName() + " with " + preferences );
        description.setText(topChoice.getDescription());
        image.setImageResource(topChoice.getDrawableId());

        TextView surpriseMe = findViewById(R.id.surprise);
        // set a click listener on that text
        surpriseMe.setOnClickListener(v -> {
            Random rand = new Random();
            int randomChoice = rand.nextInt(5);
            Coffee randomCoffee = top5Choices[randomChoice];
            while (randomCoffee.equals(lastRecommendation)) {
                randomChoice = rand.nextInt(5);
                randomCoffee = top5Choices[randomChoice];
            }
            lastRecommendation = randomCoffee;
            recommendation.setText(randomCoffee.getName() + " with " + preferences );
            description.setText(randomCoffee.getDescription());
            image.setImageResource(randomCoffee.getDrawableId());
        });
        findViewById(R.id.addToCart).setOnClickListener(v-> {
            UserService.shoppingCart.add(topChoice);
        });
        ImageView cart = findViewById(R.id.cart);
        cart.setOnClickListener(v ->{
             startActivity(new Intent(this, ShoppingCart.class));
        });

    }
}