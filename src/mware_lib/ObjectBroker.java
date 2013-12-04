package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;

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
	        return instance;
	}
	// Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
	// der Middleware aus Anwendersicht sein.
	// Parameter: Host und Port, bei dem die Dienste (Namensdienst)
	// kontaktiert werden sollen.
	
	public NameService getNameService() throws UnknownHostException, IOException {
		nameService = new OBNS(serviceHost, listenPort);
		return this.nameService;
	}
	// Liefert den Namensdienst (Stellvetreterobjekt).
	
	public void shutDown() {
	
	}
	// Beendet die Benutzung der Middleware in dieser Anwendung.
}
