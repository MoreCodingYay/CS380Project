package com.example.brewbuddycs380;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Find create account button
        Button createAccountButton = (Button) findViewById(R.id.createAccount);

        // Set onClickListener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set username, password, and confirm password EditText fields
                EditText usernameEditText = (EditText) findViewById(R.id.username);
                EditText passwordEditText = (EditText) findViewById(R.id.password);
                EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirmPassword);

                // Get text from username, password, and confirm password EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Check if any fields are empty
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    // Display toast message asking user to fill in all fields
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    // Display toast message indicating passwords do not match
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    boolean success = UserService.createAccount(username, password);
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                        usernameEditText.getText().clear();
                        passwordEditText.getText().clear();
                        confirmPasswordEditText.getText().clear();
                    } else {
                        Toast.makeText(getApplicationContext(), "Account creation failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (AccountTakenException e) {
                    Toast.makeText(getApplicationContext(), "Account username taken", Toast.LENGTH_SHORT).show();
                    return;
                } catch (UserServiceException e) {
                    Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
