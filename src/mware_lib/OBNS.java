package mware_lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import mware_lib.networking.CommConnection;
import mware_lib.references.NameServiceReference;
import mware_lib.references.RemoteServiceReference;

public class OBNS extends NameService {

	public static int threadCounter = 0;
	CommConnection client = null;
	//This is a List of Remote Services!
	ArrayList<MethodRequestService> rrsList = new ArrayList<MethodRequestService>();
	Thread tp;
	Lock connMutex;
	boolean running = true;
	private NameServiceReference nameref;
	private ServerSocket socket;
	private ObjectRequestService lrs;
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
			// this.incomingPort = socket.getLocalPort();
			this.client = new CommConnection(mySocket);
			nameref = new NameServiceReference(mySocket.getLocalAddress()
					.toString().substring(1), socket.getLocalPort(), "");
			ObjectRequestService locReq = new ObjectRequestService(socket);
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
		nameref.setname(name);

		System.out.println("nameref: " + nameref);
		
		//Initializing ouputStream
		ObjectOutput objOutput = null;
		//problems mit normal Stream, switched to byte for workaround
		byte[] receivedRef = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		try {
			//create->write->close
			objOutput = new ObjectOutputStream(outStream);
			objOutput.writeObject(nameref);
			receivedRef = outStream.toByteArray();
			System.out.println("recordRaw: " + receivedRef);
			objOutput.close();
			outStream.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[] refs = new Object[] { name, receivedRef };
		remObj.put(name, (RemoteCall) servant);
		connMutex.lock();
		System.out.println("Send: " + refs);
		client.send(refs);
		connMutex.unlock();
	}

	@Override
	public Object resolve(String name) {
		
		System.out.println("OBNS.resolve(" + name +")");
		//Multiple Threads so we have to lock the communication interface
		connMutex.lock();
		//send command
		client.send(name);
		System.out.println("Sent Object Request!");

		NameServiceReference target = null;
		try {
			//problem used with
			byte[] rawByteArray = (byte[]) client.receive();
			ByteArrayInputStream byteInStream = new ByteArrayInputStream(
					rawByteArray);
			ObjectInputStream objInStream = new ObjectInputStream(byteInStream);
			try {
				target = (NameServiceReference) objInStream.readObject();
				System.out.println("Got: " + target);
			} catch (ClassNotFoundException e) {
				System.out.println("Something went wrong while extracting class infos");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("IOException: " + target);
			// e.printStackTrace();
		} finally {
			connMutex.unlock();
		}
		System.out.println("Resolved: " + target);
		return target;
	}

	private class ObjectRequestService implements Runnable {

		/** 
	     * ListenerThread for Objects.
	     *
	     * @param args
	     */
		
		ServerSocket mySocket;

		public ObjectRequestService(ServerSocket socket) {
			mySocket = socket;
		}

		@Override
		public void run() {
			while (running) {
				try {
					System.out.println("Waiting for Connection...");
					Socket s = mySocket.accept();
					System.out.println("Connection established!");
					MethodRequestService rrs = new MethodRequestService(s);
					rrsList.add(rrs);
					Thread t = new Thread(rrs);
					threads.add(t);
					t.start();
				} catch (IOException e) {
				}
			}
		}

		public void stop() {
			running = false;
			try {
				mySocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class MethodRequestService implements Runnable {

		/**
		 * Listener Thread for Methods.
		 * Provides the RemoteCalls to the 
		 * 
		 * @param args
		 */
		
		CommConnection conn;

		public MethodRequestService(Socket socket) {
			conn = new CommConnection(socket);
			threadCounter++;
		}

		@Override
		public void run() {
			RemoteServiceReference remServRef = null;
			try {
				remServRef = (RemoteServiceReference) conn.receive();
			} catch (IOException e) {
				e.printStackTrace();
			}
			remObj.get(remServRef.getObjectName());
			RemoteCall rc = remObj.get(remServRef.getObjectName());
			Object resu = rc.callMethod(remServRef.getMethodName(), remServRef.getArguments());
			if (resu instanceof Exception) {
				System.out.println("fehler bei aufruf von" + remServRef.getMethodName());
			}
			conn.send(resu);
			while (!remServRef.getArguments().isEmpty()) {
				System.out.println(remServRef.getArguments().remove(0));
			}
		}

		public void stop() {
			running = false;
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void shutdown() {
		
		System.out.println("Stopping Object Remote Service"); 
		lrs.stop();
		
		System.out.println("Joining Service Thread"); 
		try {
			tp.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
		
		System.out.println("Interating over MethodRequestService List"); 
		for (MethodRequestService o : rrsList) {
			o.stop();
		}

		System.out.println("Stopping Object Remote Service"); 
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
	}

}
