package name_service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NameServer implements Runnable{

	Map<String, RemoteReference> nameList;
	private CommServer server;
	private Thread tp;
	private volatile boolean running;
	
	public NameServer(int port) throws IOException{
		nameList = Collections.synchronizedMap(new HashMap<String, RemoteReference>());
		server = new CommServer(port);
		tp = new Thread(this);
		
	}
	@Override
	public void run() {
		try {
			NameServerWorker worker = new NameServerWorker(server.getConnection(), nameList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start(){
		 if(!running){
			 running = true;
			 tp.start();
		 }
	}
	
	public void stop(){
		 running = false;
		 try {
			server.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	}
	
	public void join(){
		try {
			tp.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
