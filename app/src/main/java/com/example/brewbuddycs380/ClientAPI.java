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
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
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
    	if(apiSingleton==null)apiSingleton = new ClientAPI(ip,port);
    	return apiSingleton;
    	
    }
    
    public static ClientAPI getAPI() {
    	return getAPI("165.1.71.117",6666);
    }
    
    public boolean login(final String username, final String password) {
    	try {
			 String gotMessage = (getAPI().sendMessage("login "+username+" "+password+"\n"));
			 System.out.println("message gotten back is: "+gotMessage);
			 return Boolean.parseBoolean(gotMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new IllegalArgumentException("huh thats weird");
    }
    
    public boolean createUser(final String username, final String password) {
    	try {
            System.out.println("trying to create user in api");
			return Boolean.parseBoolean(getAPI().sendMessage("createUser "+username+" "+password+"\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new IllegalArgumentException("huh thats weird");
    }
    
    


}
