package mware_lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import mware_lib.networking.CommConnection;

public class OBNS extends NameService {

	public static int threadIDcnt=0;
	CommConnection client = null;
	ArrayList<RemoteRequestService> rrsList = new ArrayList<RemoteRequestService>();
	Thread tp;
	Lock connMutex;
	boolean running;
	private NameServiceReference nameref;
	private ServerSocket socket;
	private LocalRequestService lrs;
	String NameServerAdress;
	int NameServerPort;
	ArrayList<Thread> threads = new ArrayList<Thread>();
	Map<String, RemoteCall> remObj = new HashMap<String, RemoteCall>();

	public OBNS(String serviceHost, int listenPort)
			throws UnknownHostException, IOException {
			this.NameServerAdress = serviceHost;
			this.NameServerPort = listenPort;
		try {
			this.socket = new ServerSocket(0);
			connMutex = new ReentrantLock(true);
			Socket mySocket = new Socket(serviceHost, listenPort);
			//this.incomingPort = socket.getLocalPort();
			this.client = new CommConnection(mySocket);
			nameref = new NameServiceReference(mySocket.getLocalAddress().toString().substring(1), socket.getLocalPort(), "");
			LocalRequestService locReq = new LocalRequestService(socket);
			lrs = locReq;
			Thread t = new Thread(lrs);
			tp = t;
			t.start();
		} catch (IOException e) {

		}

	}

	@Override
	public void rebind(Object servant, String name) {
		System.out.println("Trying to rebind!");
		System.out.println("nameref: " + nameref);
		nameref.setname(name);
		
		System.out.println("nameref: " + nameref);
		byte[] recordRaw = null; 
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ObjectOutput objOutput = null;
		try {
			objOutput = new ObjectOutputStream(outStream);			
			objOutput.writeObject(nameref);
			recordRaw = outStream.toByteArray();
			System.out.println("recordRaw: " + recordRaw);
			objOutput.close();
			outStream.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] refs = new Object[]{name, recordRaw};
		remObj.put(name, (RemoteCall)servant);
		connMutex.lock();
		System.out.println("Send: " + refs);
		client.send(refs);
		connMutex.unlock();
	}

	@Override
	public Object resolve(String name) {
		System.out.println("Trying to resolve!");
		connMutex.lock();
		client.send(name);
		System.out.println("Sent Object Request!");
		
		NameServiceReference target = null;
		try {
			byte[] rawByteArray = (byte[])client.receive();
			System.out.println("Client.receive: check");
			 ByteArrayInputStream byteInStream = new ByteArrayInputStream(rawByteArray);
             OutputStream os; 
			 ObjectInputStream objInStream = new ObjectInputStream(byteInStream);
             try {
            	 target = (NameServiceReference)objInStream.readObject();
            	 System.out.println("Got: " + target);
                 } catch (ClassNotFoundException e) {
                         System.out.println("Deserialisierungfehler");
                         e.printStackTrace();
                 }
		} catch (IOException e) {
			System.out.println("IOException: " + target);
			// e.printStackTrace();
		}finally{
			connMutex.unlock();
			}
		System.out.println("Resolved: " + target);
		return target;

		// TODO Auto-generated method stub
	}

	private class LocalRequestService implements Runnable {

		ServerSocket mySocket;

		public LocalRequestService(ServerSocket socket) {
			mySocket = socket;
		}

		@Override
		public void run() {
			while (running) {
				try {
					System.out.println("Waiting for Connection");
					Socket s = mySocket.accept();
					System.out.println("Connection established");
					RemoteRequestService rrs = new RemoteRequestService(s);
					rrsList.add(rrs);
					Thread t = new Thread(rrs);
					threads.add(t);
					System.out.println("New Thread");
					t.start();
				} catch (IOException e) {

				}
				// TODO Auto-generated method stub
			}
		}

		public void stop() {
			running = false;

			try {
				mySocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * @param args
		 */

	}

	private class RemoteRequestService implements Runnable {

		int threadID;
		CommConnection conn;

		public RemoteRequestService(Socket socket) {
			conn = new CommConnection(socket);
			threadID = threadIDcnt++;
		}

		@Override
		public void run() {
			System.out.println("RemoteRequestService(" + threadID
					+ ") ready for RemoteCall");
			RemoteServiceReference rsr = null;
			try {
				rsr = (RemoteServiceReference) conn.receive();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			remObj.get(rsr.getObjName());
			RemoteCall rc = remObj.get(rsr.getObjName());
			Serializable resu = rc.callMethod(rsr.getMethod(), rsr.getParams());
			if (resu instanceof Exception) {
				// TODO
			}
			conn.send(resu);

			while (!rsr.getParams().isEmpty()) {
				System.out.println(rsr.getParams().remove(0));
			}
		}

		public void stop() {
			running = false;

			try {
				conn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * @param args
	 */
	public void shutdown() {
		lrs.stop();
		try {
			tp.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		running = false;
		for (RemoteRequestService o : rrsList) {
			o.stop();
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
