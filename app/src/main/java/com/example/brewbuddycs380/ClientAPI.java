package com.example.brewbuddycs380;

import java.io.*;
import java.net.*;

/**
 * ClientAPI class for communication with the server.
 */
public class ClientAPI {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Starts the connection with the server.
     *
     * @param ip   the IP address of the server
     * @param port the port number for the connection
     * @throws UnknownHostException if the IP address is invalid
     * @throws IOException          if an I/O error occurs
     */
    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Sends a message to the server and receives the response.
     *
     * @param msg the message to send
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    /**
     * Stops the connection with the server.
     */
    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClientAPI apiSingleton;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param ip   the IP address of the server
     * @param port the port number for the connection
     */
    private ClientAPI(String ip, int port) {
        try {
            startConnection(ip, port);
        } catch (UnknownHostException e) {
            System.out.println(" exception : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(" exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the ClientAPI singleton instance with the specified IP address and port number.
     * If the singleton instance is null or the client socket is closed, a new instance is created.
     *
     * @param ip   the IP address of the server
     * @param port the port number for the connection
     * @return the ClientAPI singleton instance
     */
    public static ClientAPI getAPI(String ip, int port) {
        if (apiSingleton == null || apiSingleton.clientSocket.isClosed()) {
            apiSingleton = new ClientAPI(ip, port);
        }
        return apiSingleton;
    }

    /**
     * Returns the ClientAPI singleton instance with the default IP address and port number.
     *
     * @return the ClientAPI singleton instance
     */
    public static ClientAPI getAPI() {
        return getAPI("165.1.71.117", 6666);
    }

    /**
     * Logs in with the given username and password.
     *
     * @param username the username to log in with
     * @param password the password to log in with
     * @return true if the login is successful, false otherwise
     */
    public boolean login(final String username, final String password) {
        try {
            String gotMessage = (this.sendMessage("login " + username + " " + password + "\n"));
            return Boolean.parseBoolean(gotMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopConnection();
        }
        throw new IllegalArgumentException("huh that's weird");
    }

    /**
     * Creates a new user with the given username and password.
     *
     * @param username the username for the new user
     * @param password the password for the new user
     * @return true if the user creation is successful, false otherwise
     */
    public boolean createUser(final String username, final String password) {
        try {
            return Boolean.parseBoolean(this.sendMessage("createUser " + username + " " + password + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopConnection();
        }
        throw new IllegalArgumentException("Illegal Error");
    }

    /**
     * Retrieves the preferences for the given username.
     *
     * @param username the username to retrieve the preferences for
     * @return the preferences as a string
     */
    public String getPreferences(final String username) {
        try {
            return this.sendMessage("preferences " + username + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            stopConnection();
        }
    }

    /**
     * Sets the preferences for the given username.
     *
     * @param username    the username to set the preferences for
     * @param preferences the preferences to set
     * @return true if setting the preferences is successful, false otherwise
     */
    public boolean setPreferences(final String username, final String preferences) {
        try {
            return Boolean.parseBoolean(sendMessage("setPreferences " + username + " " + preferences + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            stopConnection();
        }
    }
}
