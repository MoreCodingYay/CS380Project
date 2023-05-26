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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
    
    public boolean login(String username, String password) {
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
    
    public boolean createUser(String username, String password) {
    	try {
			return Boolean.parseBoolean(getAPI().sendMessage("createUser "+username+" "+password+"\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new IllegalArgumentException("huh thats weird");
    }
    
    
    /**
     * test driver method
     * @param args
     */
    public static void main(String[] args) {
    	//System.out.println("resutlt create "+getAPI().createUser("steiner4", "123"));
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("result: "+getAPI().login("steiner4", "123"));
    	
    }
}
