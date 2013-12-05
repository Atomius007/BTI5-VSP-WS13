package name_service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class NameServerWorker implements Runnable {
    private NameServerConnection connection;
    private Map<String, Serializable> nameList; // the nameServer's registry
    
    private Thread tp;
    private volatile boolean running = false;

	public NameServerWorker(NameServerConnection connection,
			Map<String, Serializable> nameList) {
		this.connection = connection;
        this.nameList = nameList;
        this.tp = new Thread(this);
	}
	
    public void start() {
        if (!running) {
            running = true;
            tp.start();
        }
    }
    
    public void stop() {
        running = false;
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void join() {
        try {
            tp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void run() {
		System.out.println("WorkerRun!");
		Serializable message = connection.receive();
		System.out.println("Received: " + message);
		System.out.println("Running: " + running);
		while (true) {
			if (message instanceof String) {
				// resolve
				System.out.println("RESOLVE!");
				Object o = nameList.get((String) message);
				System.out.println("Resolving " + message);
				if (o == null) {

				} else {
					connection.send((Serializable) o);
				}
			} else {
				// rebind
				System.out.println("Binding " + message.toString());
				Serializable[] newRef = (Serializable[]) message;
				
				nameList.put((String) newRef[0], newRef);
			}

		}

	}
	
	public Serializable checkMessage(Serializable message){
		
		return message;
		
	}

}
