package mware_lib;

import java.io.IOException;
public class ObjectBroker { // - Front-End der Middleware -
	
	private ObjectBroker(String serviceHost, int listenPort) {
			this.serviceHost = serviceHost;
			this.listenPort = listenPort;
		}
	
	private static ObjectBroker instance;
	public OBNS nameService;
	public final String serviceHost;
	public final int listenPort;
	
	public static ObjectBroker init(String serviceHost, int listenPort) {
		 if (instance == null) {
	        	instance = new ObjectBroker(serviceHost, listenPort);
	        }
		 System.out.println("Init ObjectBroker!");
	        return instance;
	}
	// Das hier zur�ckgelieferte Objekt soll der zentrale Einstiegspunkt
	// der Middleware aus Anwendersicht sein.
	// Parameter: Host und Port, bei dem die Dienste (Namensdienst)
	// kontaktiert werden sollen.
	
	public NameService getNameService() {
		try {
			nameService = new OBNS(serviceHost, listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.nameService;
	}
	// Liefert den Namensdienst (Stellvetreterobjekt).
	
	public void shutDown() {
		System.out.println("Invoking Shutdown.");
		this.nameService.shutdown();
	}
	// Beendet die Benutzung der Middleware in dieser Anwendung.
}
