package bank_access;

import java.io.Serializable;

public class ManagerStub extends ManagerImplBase {

	String host, name;
	int port;
	
	public ManagerStub(String host, int port, String name){
		this.host = host;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable callMethod(String methodName, Serializable[] params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
