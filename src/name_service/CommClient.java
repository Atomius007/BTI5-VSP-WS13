package name_service;

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
	public Serializable callMethod(String methodName, Serializable params) throws IOException, ClassNotFoundException, NoRemoteMessageException {
		RemoteMessage invMsg = null;
		RemoteMessage rplMsg = null;
		
		invMsg = new InvocationMessage(methodName, params);
		send(invMsg);
		rplMsg = receive();
		
		return rplMsg;
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
		mySocket.close();
	}

}