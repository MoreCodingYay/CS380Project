package com.example.brewbuddycs380;

import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import java.util.Random;
import java.util.Set;

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
        // Changes the status bar color to enhance the visual appearance
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar_main));
        }

        setContentView(R.layout.activity_reccomendation_screen);
        TextView recommendation = findViewById(R.id.recommendation);
        TextView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.coffee);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(4.5F);

        String userPreferencesString = null;
        try {
            userPreferencesString = UserService.getUserPreferences(UserService.getUserName());
        } catch (UserServiceException e) {
            Toast.makeText(getApplicationContext(), "Preferences network error: not retrieved ", Toast.LENGTH_SHORT).show();
        }

        Set<Properties> userPreferences = CoffeeRecommender.stringToUserPreference(userPreferencesString);
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
            ratingBar.setRating(3 + (2*(rand.nextFloat())));
            recommendation.setText(randomCoffee.getName() + " with " + preferences );
            description.setText(randomCoffee.getDescription());
            image.setImageResource(randomCoffee.getDrawableId());
        });

        ImageView home = findViewById(R.id.home);
        /**
         * Set a click listener on the "home" image.
         * When clicked, goes back to questions
         */
        home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
        });
    }
}
