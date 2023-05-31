package com.example.brewbuddycs380;



import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.*;
public class ClientAPI {
    //	public static final String ip = "10.10.43.248";
//	public static final int port = 6666;
//
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
        System.out.println("tryinc to start connection");
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        System.out.println("is the client closed: "+clientSocket.isClosed());
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection()  {
        try {
            in.close();
            out.close();

            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static ClientAPI apiSingleton;
    public ClientAPI(String ip, int port){
        try {
            startConnection(ip,port);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println(" exception : "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(" exception : "+e.getMessage());
            e.printStackTrace();
        }

    }

    public static ClientAPI getAPI(String ip, int port) {
        if(apiSingleton==null||apiSingleton.clientSocket.isClosed())apiSingleton = new ClientAPI(ip,port);
        return apiSingleton;

    }

    public static ClientAPI getAPI() {
        return getAPI("165.1.71.117",6666);
    }

    public boolean login(final String username, final String password) {
        try {
            String gotMessage = (this.sendMessage("login "+username+" "+password+"\n"));
            System.out.println("message gotten back is: "+gotMessage);
            return Boolean.parseBoolean(gotMessage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            stopConnection();
        }
        throw new IllegalArgumentException("huh thats weird");
    }

    public boolean createUser(final String username, final String password) {
        try {
            System.out.println("trying to create user in api");
            return Boolean.parseBoolean(this.sendMessage("createUser "+username+" "+password+"\n"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            stopConnection();
        }
        throw new IllegalArgumentException("huh thats weird");
    }
    public String getPreferences(final String username) {
        try {
            return this.sendMessage("preferences "+username+"\n");
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            stopConnection();
        }
    }

    public boolean setPreferences(final String username, final String preferences){
        try{
            System.out.println("sending the set prefs message");
            return Boolean.parseBoolean(sendMessage("setPreferences "+username+" "+preferences+"\n"));
        }catch(IOException e) {
            System.out.println("prefs didn't work");
            e.printStackTrace();
            return false;
        }finally{
            stopConnection();
        }
    }
    public static void test() {
        ClientAPI api = getAPI();
        System.out.println("could create user? "+api.createUser("testUser","123"));
        System.out.println("could log in? " + api.login("testUser", "123"));
    }




}

