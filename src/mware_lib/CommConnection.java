package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CommConnection {
    private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public CommConnection(Socket clientSocket) throws IOException {
	    this.clientSocket = clientSocket;
	    out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
	}
	
	public RemoteMessage receive() throws IOException, ClassNotFoundException, NoRemoteMessageException {
	    Object rcvObj = in.readObject();
	    if (!(rcvObj instanceof RemoteMessage)) {
	        throw new NoRemoteMessageException("No RemoteMessage: " + rcvObj.toString());
	    }
		return (RemoteMessage) rcvObj;
	}
	
	public void send(RemoteMessage message) throws IOException {
		out.writeObject(message);
		out.flush();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}
}