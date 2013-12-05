package name_service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	public Object receive() throws IOException {
        Object rcvObj = null;
		try {
			rcvObj = in.readObject();
			//in.close();
			//clientSocket.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return (Object) rcvObj;
	}

	public void send(Object message){
		try {
			out.writeObject(message);
			out.flush();
			//out.close();
			//clientSocket.close();
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
