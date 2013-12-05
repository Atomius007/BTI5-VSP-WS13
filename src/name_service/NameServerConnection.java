package name_service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class NameServerConnection {
	
    private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public NameServerConnection(Socket socket){
		this.clientSocket = socket;
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Serializable receive() {
        Object rcvObj = null;
		try {
			rcvObj = in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return (Serializable) rcvObj;
	}

	public void send(Serializable message){
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}
}
