package name_service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NameServer implements Runnable{

	Map<String, RemoteReference> nameList;
	private CommServer server;
	private Thread thread;
	private volatile boolean running;
	
	public NameServer(int port) throws IOException{
		nameList = Collections.synchronizedMap(new HashMap<String, RemoteReference>());
		server = new CommServer(port);
		thread = new Thread(this);
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


}
