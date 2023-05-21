package com.example.brewbuddycs380;

import java.sql.*;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity{

    // probably insecure way of connecting to db, might have to change later
    static final String URL = "jdbc:mysql://sql9619545@sql9.freemysqlhosting.net/sql9619545";
    static final String USER = "sql9619545";
    static final String PASS = "TALaShDLMD";

    // Connecting will look something like this
    /*
     try {
                    Connection connect = DriverManager.getConnection(URL, USER, PASS);
                    System.out.println("Connected to DB");
                } catch (SQLException err) {
                    err.printStackTrace();
                    System.out.println("SQL Error Detected\t");
                }
     */

    @Override
    // first part that runs when app runs
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
            public void onClick(View v) {
                // right now this just goes to the get started page
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));

            }
        });

        TextView createAccountTxt = findViewById(R.id.createAccount);
        // set a click listener on that text
        createAccountTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to create account activity
                startActivity(new Intent(MainActivity.this, createAccount.class));

            }
        });
    }
}