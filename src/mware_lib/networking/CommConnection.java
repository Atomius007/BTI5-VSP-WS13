package mware_lib.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

public class CommConnection {
	
    private Socket clientSocket;
    private InputStream inStream;
    private OutputStream outStream;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public CommConnection(Socket socket){
		this.clientSocket = socket;
		try {
			outStream = clientSocket.getOutputStream();
			out = new ObjectOutputStream(outStream);
			out.flush();
			inStream = clientSocket.getInputStream();
            in = new ObjectInputStream(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object receive() throws IOException {
        Object rcvObj = null;
		try {
			rcvObj = in.readObject();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}
}
