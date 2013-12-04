package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;

public class OBNS extends NameService {
	
	
	private CommClient client;
	
	public OBNS(String serviceHost, int listenPort) throws UnknownHostException, IOException{
		this.client = new CommClient(serviceHost, listenPort);
	}
	@Override
	public void rebind(Object servant, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object resolve(String name) {
		Object stub = null;
		
		
		
		// TODO Auto-generated method stub
		return stub;
	}
	
	public Object createStub(){
		return client;
		
	}

}
