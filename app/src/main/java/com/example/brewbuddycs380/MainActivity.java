package com.example.brewbuddycs380;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity{

    @Override
    // first part that runs when app runs
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the app view to the main activity (login page)
        setContentView(R.layout.activity_main);

        // find login button from main activity
        Button loginBtn = (Button) findViewById(R.id.login);
        // set a click listener on that button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // right now this just goes to the get started page
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));

            }
        });
    }
}