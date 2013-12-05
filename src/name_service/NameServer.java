package name_service;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NameServer{

	Map<String, Object> nameList = new HashMap<String, Object>();
	private ServerSocket srvSocket;
	Lock mutex;
	static int serverListenPort;
	
	public NameServer(int port) throws IOException{
		mutex = new ReentrantLock(true);
		System.out.println("init name server");
		srvSocket = new ServerSocket(serverListenPort);
		
	}
	
	public NameServerConnection getConnection() throws IOException{
		return new NameServerConnection(srvSocket.accept());
	}
	
	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("Please specify a port !");
			System.exit(1);
		}
		serverListenPort = Integer.parseInt(args[0]);
		System.out.println("Port: " + serverListenPort);
		try {
			NameServer ns = new NameServer(serverListenPort);
			ns.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		while(true){
			try {
				NameServerWorker worker = new NameServerWorker(getConnection());
				Thread workerThread = new Thread(worker);
				workerThread.start();
				System.out.println("Worker started!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
		}
	}
	   

    private class NameServerWorker implements Runnable {
        private NameServerConnection connection;
        
    	public NameServerWorker(NameServerConnection connection) {
    		this.connection = connection;
    	}
   
    	@Override
    	public void run() {
    		
    		//message = connection.receive();
    		//System.out.println("Received: " + message);
    		//System.out.println("Running: " + running);
    		while (true) {
    			System.out.println("WorkerRun!");
        		Object message = null;
    			try {
    				message = connection.receive();
    			} catch (IOException e) {
    				System.out.println("Received: " + message);
    				//e.printStackTrace();
    				break;
    			}
    			 System.out.println("received: " + message);
    			if (message instanceof String) {
    				// resolve
    				mutex.lock();
    				Object o = nameList.get((String) message);
    				mutex.unlock();
    				System.out.println("Resolving " + message);
    				if (o == null) {

    				} else {
    					//TODO
    				}
    				connection.send(o);
    			} else {
    				// rebind
    				mutex.lock();
    				System.out.println("Binding " + message.toString());
    				Object[] newRef = (Object[]) message;
    				
    				nameList.put((String) newRef[0], newRef[1]);
    				mutex.unlock();
    				System.out.println("Object zu Liste hinzugefügt " + message.toString());
    			}

    		}

    	}

    }



}
