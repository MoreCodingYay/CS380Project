package com.example.brewbuddycs380;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
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

    public static String getUsername() {
        return savedUsername;
    }

    private volatile static String savedUsername = "";
    private volatile static String savedPrefs = "";

    public  static LoggedInState getLoggedInState() {
        return state;
    }

    private volatile static LoggedInState state = LoggedInState.notLoggedIn;

    public volatile static ArrayList<Coffee> shoppingCart = new ArrayList<Coffee>();


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
            System.out.println("else??? "+state);
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
     * @return whether the user has taken the preferences test before and has some on file
     */
    public static boolean haveRecordedPrefs(){
        return state==LoggedInState.loggedInHavePrefs;
    }
    /**
     * @return whether the user is logged in
     */
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    private volatile static boolean loggedIn = false;
    /**
     * Creates a new user account with the given username and password.
     *
     * @param username the username for the account
     * @param password the password for the account
     * @return true if the account is successfully created, false otherwise
     * @throws AccountTakenException if the username is already taken
     * @throws UserServiceException if an error occurs while creating the account
     */
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

    /**
     * Logs in with the given username and password.
     *
     * @param username the username for the account
     * @param password the password for the account
     * @return true if the login is successful, false otherwise
     * @throws UserServiceException if an error occurs while logging in
     */
    public static boolean login(final String username, final String password) throws UserServiceException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {
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
                System.out.println("Updating user state: "+ UserService.getLoggedInState());

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


    /**
     * adds coffee to the cart
     * @param coffee
     */
    public static void addToCart(Coffee coffee){
        shoppingCart.add(coffee);
    }

    /**
     * updates the user's preferences with the coffee's they selected.
     * @return
     */
    public static boolean checkOut(){
        if(shoppingCart.size()==0)return false;
        Map<Properties,Integer> prefs = CoffeeRecommender.stringToPreferencesAndWeights(getPrefs());

        for(Coffee i : shoppingCart) {
            CoffeeRecommender.updatePrefWeightHashmapWithCoffee(prefs, i);
        }
        shoppingCart.clear();
        String oldPrefs = getPrefs(), newPrefs = CoffeeRecommender.preferencWeightMapToString(prefs);
        System.out.println("old: "+oldPrefs+" new: "+newPrefs);
        updatePreferences(CoffeeRecommender.preferencWeightMapToString(prefs));
        return true;
    }
    /**
     * Helper method to hash a password using the SHA-512 algorithm.
     *
     * @param password the password to be hashed
     * @return the hashed password as a hexadecimal string
     * @throws NoSuchAlgorithmException if the SHA-512 algorithm is not available
     */
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


    public UserService(){
        System.out.println("constructing user service");
    }

}

