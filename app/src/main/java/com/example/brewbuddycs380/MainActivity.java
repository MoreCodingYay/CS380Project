package com.example.brewbuddycs380;

import java.sql.*;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

/**
 * The main activity of the BrewBuddy app, responsible for user login and account creation.
 */
public class MainActivity extends AppCompatActivity{

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState the saved instance state Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // changes the status bar color so it looks prettier
        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar_main));
        }

        // sets the app view to the main activity (login page)
        setContentView(R.layout.activity_main);


        // find login button from main activity
        Button loginBtn = (Button) findViewById(R.id.login);
        // set a click listener on that button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Set username and passwordEditText fields
                EditText usernameEditText = (EditText) findViewById(R.id.username);
                EditText passwordEditText = (EditText) findViewById(R.id.password);

                // Get text from username and passwordEditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if any fields are empty
                if (username.isEmpty() || password.isEmpty()) {
                    // Display toast message asking user to fill in all fields
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = false;
                try {
                    success = UserService.login(username, password);
                    if (success) {
                        usernameEditText.getText().clear();
                        passwordEditText.getText().clear();
                        System.out.println("logged in state is: "+UserService.getLoggedInState());
                        if(UserService.getLoggedInState()==LoggedInState.loggedInPrefsRetrieved){

                            //if the logged-in user already has preferences, move to the recommendation screen
                            startActivity(new Intent(MainActivity.this, RecommendationScreen.class));
                        }else{
                            //if they don't have any preferences, move to the question screen
                            startActivity(new Intent(MainActivity.this, QuestionActivity.class));
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (UserServiceException e) {
                    Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

                }

            }
        });

        TextView createAccountTxt = findViewById(R.id.createAccount);
        // set a click listener on that text
        createAccountTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to create account activity
                startActivity(new Intent(MainActivity.this, CreateAccount.class));

            }
        });
    }
}
