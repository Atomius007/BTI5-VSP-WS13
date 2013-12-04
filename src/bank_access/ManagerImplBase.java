package bank_access;

import java.io.Serializable;
import java.util.ArrayList;

import mware_lib.*;

public abstract class ManagerImplBase implements RemoteCall {
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		ManagerStub stub = new ManagerStub((RemoteReference)rawObjectRef); 
		return stub;
		}
	
	@Override
	public Serializable callMethod(String methodName, ArrayList<Object> params){
		return params;
		
	}
	
	
}
