package mware_lib;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import mware_lib.networking.CommClient;

public class OBNS extends NameService {
	
	private CommClient client;
	private int incomingPort;
	private int listenPort;
	private Thread tp;
	private ServerSocket socket;
	
	
	public OBNS(String serviceHost, int listenPort) throws UnknownHostException, IOException{
		this.client = new CommClient(serviceHost, listenPort);
		this.socket = new ServerSocket(0);
		try {
			this.incomingPort = socket.getLocalPort();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		
	}
	
	@Override
	public void rebind(Object servant, String name) {
		System.out.println("Trying to rebind!");
		try {
			Serializable ref = new RemoteReference(InetAddress.getLocalHost().getHostAddress(), socket.getLocalPort(), name);
			client.send((Serializable)ref);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object resolve(String name) {
		System.out.println("Trying to resolve!");
		Object stub = null;
		client.send(name);
		stub = client.receive();
		System.out.println("Resolved: " + stub);
		
		
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
