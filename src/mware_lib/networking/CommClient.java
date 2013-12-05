package mware_lib.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import mware_lib.RemoteCall;
import mware_lib.messages.InvocationMessage;
import mware_lib.messages.RemoteMessage;

public class CommClient implements RemoteCall{
	
	private Socket mySocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public CommClient(String host, int port) throws UnknownHostException, IOException {
		mySocket = new Socket(host, port);
		
		out = new ObjectOutputStream(mySocket.getOutputStream());
		in = new ObjectInputStream(mySocket.getInputStream());
	}

	public Serializable receive() throws IOException {
        Object rcvObj = null;
        System.out.println("Trying to receive!");
		try {
			rcvObj = in.readObject();
			//in.close();
			//mySocket.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ClassNotFound!");
			
		}
		System.out.println("Received: " + rcvObj);
        return (Serializable) rcvObj;
	}
	
	public void send(Serializable message){
		System.out.println("Trying to send!");
		try {
			out.writeObject(message);
			out.flush();
			//out.close();
			//mySocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sent!");
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

	@Override
	public Serializable callMethod(String methodName, ArrayList<Object> params) {
		System.out.println("CallMethod!");
		Serializable invMsg = new InvocationMessage(methodName, params);
		Serializable retMsg = null;
		send(invMsg);
		try {
			retMsg = receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retMsg;
	}



}