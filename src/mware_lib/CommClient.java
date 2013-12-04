package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommClient implements RemoteCall{
	
	private Socket mySocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public CommClient(String host, int port) throws UnknownHostException, IOException {
		mySocket = new Socket(host, port);
		
		out = new ObjectOutputStream(mySocket.getOutputStream());
		in = new ObjectInputStream(mySocket.getInputStream());
	}

	@Override
	public Serializable callMethod(String methodName, Serializable[] params){
		RemoteMessage invMsg = null;
		RemoteMessage rplMsg = null;
		
		invMsg = new InvocationMessage(methodName, params);
		send(invMsg);
		rplMsg = receive();
		
		return rplMsg;
	}
	
	public RemoteMessage receive() {
        Object rcvObj = null;
		try {
			rcvObj = in.readObject();
	        if (!(rcvObj instanceof RemoteMessage)) {
	            
	        }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return (RemoteMessage) rcvObj;
	}
	
	public void send(RemoteMessage message){
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			in.close();
			out.close();
			mySocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}