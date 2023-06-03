package com.example.brewbuddycs380;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

/**
 * An activity that displays coffee recommendations based on user preferences in the BrewBuddy app.
 */
public class RecommendationScreen extends AppCompatActivity {

    private Coffee lastRecommendation;

    /**
     * Called when the activity is created.
     * Sets the layout for the activity and initializes the views.
     *
     * @param savedInstanceState the saved instance state Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reccomendation_screen);
        TextView recommendation = findViewById(R.id.recommendation);
        TextView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.coffee);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(4.5F);

        Properties[] selectedPropertiesArray = (Properties[]) getIntent().getSerializableExtra("selectedProperties");
        EnumSet<Properties> userPreferences = EnumSet.copyOf(Arrays.asList(selectedPropertiesArray));

        Coffee topChoice = CoffeeRecommender.recommendTopCoffee(userPreferences);
        Coffee[] top5Choices = CoffeeRecommender.recommend5Coffee(userPreferences);
        String preferences = CoffeeRecommender.getPreferencesString(userPreferences);

        /**
         * Set the text for the recommendation TextView with the top choice coffee and user preferences.
         */
        recommendation.setText(topChoice.getName() + " with " + preferences );
        description.setText(topChoice.getDescription());
        image.setImageResource(topChoice.getDrawableId());

        TextView surpriseMe = findViewById(R.id.surprise);
        /**
         * Set a click listener on the "Surprise Me" text.
         * When clicked, randomly selects a coffee from the top 5 choices and displays it as a recommendation.
         * Ensures that the same recommendation is not repeated consecutively.
         */
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
    }
}
