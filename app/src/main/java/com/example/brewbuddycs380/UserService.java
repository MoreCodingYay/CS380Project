package com.example.brewbuddycs380;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class AccountTakenException extends Exception {
    public AccountTakenException(String message) {
        super(message);
    }
}

class UserServiceException extends Exception {
    public UserServiceException(String message) {
        super(message);
    }
}

public class UserService {
    // Connection details for the MySQL server
    static final String URL = "jdbc:mysql://sql9.freemysqlhosting.net/sql9619545";
    static final String USER = "sql9619545";
    static final String PASS = "TALaShDLMD";

    public static String getUsername() {
        return savedUsername;
    }

    private static String savedUsername = "";
    private static String savedPrefs = "";

    public static LoggedInState getLoggedInState() {
        return state;
    }

    private static LoggedInState state = LoggedInState.notLoggedIn;

    /**
     * logged in state state machine function
     * if logged in but haven't retrived prefs, retrieves them updates state
     * if already retrieved prefs, returns them
     * otherwise, returns empty string
     * @return
     */
    public static String getPrefs(){
        System.out.println("runnig getprefs" + (state==LoggedInState.loggedInNoPrefs));
        if(state==LoggedInState.loggedInPrefsRetrieved){
            System.out.println("already retrieved");
            return savedPrefs;
        }else if(state==LoggedInState.loggedInNoPrefs||state==LoggedInState.loggedInHavePrefs){
            System.out.println("logging in no prefs getting them");
            savedPrefs = ClientAPI.getAPI().getPreferences(savedUsername);

            if(savedPrefs=="null"||savedPrefs=="NULL"||savedPrefs==""||savedPrefs=="Null"){
                //if there aren't any prefs saved for the user
                //only called if a user created an account and then never submitted their questionaire
                state=LoggedInState.loggedInNoPrefs;
                System.out.println("didn't get prefs");
                //makes sure saved prefs is back to being our actual null state
                savedPrefs="";

            }else {
                //if they do have non null preferences
                state = LoggedInState.loggedInPrefsRetrieved;
                System.out.println("successfully retrieved prefs");
            }
            return savedPrefs;
        }else{
            System.out.println("else???");
            return "";
        }
    }

    /**
     * updates preferences in the database in a thread, and updates the preferences variable in userservice.
     * @param preferences
     * @return
     */
    public static boolean updatePreferences(String preferences){
        new Thread(() -> {
            System.out.println("running anonymous thread");
            System.out.println("api req is: "+ClientAPI.getAPI().setPreferences(UserService.getUsername(), preferences));
        }).start();
        savedPrefs=preferences;
        state=LoggedInState.loggedInPrefsRetrieved;
        return true;
    }
    /**
     *
     * @return whether or not the user has taken the preferences test before and has some on file
     */
    public static boolean haveRecordedPrefs(){
        return state==LoggedInState.loggedInHavePrefs;
    }
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    private static boolean loggedIn = false;
    // Method to create a new account with the given username and password
    public static boolean createAccount(final String username, final String password) throws AccountTakenException, UserServiceException {
        // Have to use executor because database won't connect on main network thread for some reason
        // This object makes a new thread just for the database
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {
            String hashedPassword = hashPassword(password);
            boolean success =  ClientAPI.getAPI().createUser(username, hashedPassword);
            if(success){
                loggedIn=true;
                savedUsername=username;

            }
            return success;

//            try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
//                // Check if the username is already taken
//                String checkSql = "SELECT * FROM sql9619545.logins WHERE username = ?";
//                PreparedStatement checkStatement = connection.prepareStatement(checkSql);
//                checkStatement.setString(1, username);
//                ResultSet checkResultSet = checkStatement.executeQuery();
//                if (checkResultSet.next()) {
//                    // Username is already taken
//                    throw new AccountTakenException("Username is already taken");
//                }
//
//                // Hash the password using the SHA-512 algorithm
//                String hashedPassword = hashPassword(password);
//                // SQL statement to insert a new record into the logins table
//                String sql = "INSERT INTO sql9619545.logins (username, password) VALUES (?, ?)";
//                PreparedStatement statement = connection.prepareStatement(sql);
//                statement.setString(1, username);
//                statement.setString(2, hashedPassword);
//                int rowsInserted = statement.executeUpdate();
//                return rowsInserted > 0;
//            } catch (SQLException e) {
//                throw new UserServiceException("SQLException: " + e.getMessage());
//            } catch (NoSuchAlgorithmException e) {
//                throw new UserServiceException("NoSuchAlgorithmException: " + e.getMessage());
//            }


        });
        try {
            return future.get();
        } catch (Exception e) {
            if (e.getCause() instanceof AccountTakenException) {
                throw (AccountTakenException) e.getCause();
            } else if (e.getCause() instanceof UserServiceException) {
                throw (UserServiceException) e.getCause();
            }
            throw new UserServiceException("Unknown exception: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    // Method to log in with the given username and password
    public static boolean login(final String username, final String password) throws UserServiceException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {

            /*
            try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
                // Hash the password using the SHA-512 algorithm
                String hashedPassword = hashPassword(password);
                // SQL statement to query the logins table for a record with the given username and hashed password
                String sql = "SELECT * FROM sql9619545.logins WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                throw new UserServiceException("SQLException: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new UserServiceException("NoSuchAlgorithmException: " + e.getMessage());
            }

             */
            String hashedPassword = null;
            try {
                hashedPassword = hashPassword(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            boolean loggedIn = ClientAPI.getAPI().login(username, hashedPassword);
            savedUsername = username;
            state=LoggedInState.loggedInNoPrefs;
            getPrefs();
            return loggedIn;
        });
        try {
            if(future.get()){
                System.out.println("updateing user state: "+ UserService.getLoggedInState());

                loggedIn=true;
                return true;
            }
            savedUsername = "";
            return false;

        } catch (Exception e) {
            if (e.getCause() instanceof UserServiceException) {
                throw (UserServiceException) e.getCause();
            }
            throw new UserServiceException("Unknown exception: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    // Helper method to hash a password using the SHA-512 algorithm
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Get an instance of the SHA-512 message digest
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());

        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        // Convert each byte of the message digest to a two-digit hexadecimal string
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        // Return the resulting hexadecimal string
        return sb.toString();
    }
}

