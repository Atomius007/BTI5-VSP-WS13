package name_service;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class NameServer implements Runnable{

	Map<String, Serializable> nameList;
	private ServerSocket srvSocket;
	private Thread tp;
	Lock mutex;
	private volatile boolean running;
	
	public NameServer(int port) throws IOException{
		nameList = Collections.synchronizedMap(new HashMap<String, Serializable>());
		srvSocket = new ServerSocket(port);
		
	}
	
	public NameServerConnection getConnection() throws IOException{
		return new NameServerConnection(srvSocket.accept());
	}
	
	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("Please specify a port !");
			System.exit(1);
		}
		int port = Integer.parseInt(args[0]);
		System.out.println("Port: " + port);
		try {
			NameServer ns = new NameServer(port);
			ns.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		while(true){
			NameServerWorker worker = null;

			try {
				worker = new NameServerWorker(getConnection(), nameList);
				Thread workerThread = new Thread(worker);
				workerThread.start();
				System.out.println("Worker started!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
		}
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
            srvSocket.close();
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
    	System.out.println("Running!");

        while (running) {
            try {
                // accept client and start worker
            	System.out.println("Starting Worker!");
                NameServerWorker worker = new NameServerWorker(getConnection(), nameList);
                worker.start();

            } catch (IOException e) { // getConnection()
                //logger.logErr("Accepting client failed: " + e.getMessage());
                 e.printStackTrace();
            }
        }

    }


}
