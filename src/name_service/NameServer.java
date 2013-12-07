package name_service;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NameServer {

	Map<String, Object> nameList = new HashMap<String, Object>();
	private ServerSocket srvSocket;
	Lock mutex;
	private boolean listening = true;
	static int serverListenPort;

	public NameServer(int port) throws IOException {
		mutex = new ReentrantLock(true);
		System.out.println("init name server");
		srvSocket = new ServerSocket(serverListenPort);

	}

	public NameServerConnection getConnection() throws IOException {
		return new NameServerConnection(srvSocket.accept());
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please specify a port !");
			System.exit(1);
		}
		serverListenPort = Integer.parseInt(args[0]);
		System.out.println("Port: " + serverListenPort);
		try {
			NameServer ns = new NameServer(serverListenPort);
			ns.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		while (listening) {
			try {
				NameServerWorker worker = new NameServerWorker(getConnection());
				Thread workerThread = new Thread(worker);
				workerThread.start();
				System.out.println("Worker started!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Internal Class - uses mutex locking.
	private class NameServerWorker implements Runnable {
		private NameServerConnection connection;

		public NameServerWorker(NameServerConnection connection) {
			this.connection = connection;
		}

		@Override
		public void run() {
			while (listening) {
				// Listening of Socket and waiting for client to connect!
				System.out.println("Start Worker.");
				Object message = null;
				try {
					message = connection.receive();
					System.out.println("Message received: " + message);
				} catch (IOException e) {
					break;
				}

				if (message instanceof String) {
					// RESOLVE!

					System.out.println("Resolve");
					mutex.lock();
					Object o = nameList.get((String) message);
					mutex.unlock();
					if (o == null) {
						System.out.println(message.toString() + "not found");
					} else {
						System.out.println("Found!");
					}
					connection.send(o);

					// RESOLVE END;

				} else {
					// REBIND!

					System.out.println("Rebind");
					mutex.lock();
					System.out.println("Binding " + message.toString());
					Object[] newRef = (Object[]) message;
					nameList.put((String) newRef[0], newRef[1]);
					mutex.unlock();
					System.out.println("Object zu Liste hinzugefügt "
							+ message.toString());

					// RESOLVE END!
				}

			}

		}

	}

}
