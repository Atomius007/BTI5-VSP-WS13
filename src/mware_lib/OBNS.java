package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import mware_lib.networking.CommClient;

public class OBNS extends NameService {
	
	private CommClient client;
	private int incomingPort;
	private int listenPort;
	private Thread tp;
	
	
	public OBNS(String serviceHost, int listenPort) throws UnknownHostException, IOException{
		this.client = new CommClient(serviceHost, listenPort);
	}
	
	@Override
	public void rebind(Object servant, String name) {
		RemoteReference ref = null;
	}

	@Override
	public Object resolve(String name) {
		Object stub = null;
		
		
		
		// TODO Auto-generated method stub
		return stub;
	}
	
	public Object createStub(){
		return client;
		
	}
	
	public int findFreePort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		try {
			return socket.getLocalPort();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

}
