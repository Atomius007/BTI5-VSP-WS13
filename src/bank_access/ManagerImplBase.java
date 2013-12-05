package bank_access;

import java.io.Serializable;
import java.util.ArrayList;

import mware_lib.*;

public abstract class ManagerImplBase implements RemoteCall {
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		ManagerStub stub = new ManagerStub((NameServiceReference)rawObjectRef); 
		return stub;
		}
	
	@Override
	public Serializable callMethod(String methodName, ArrayList<Object> params){
		Object resu = null;
		if(methodName.equals("createAccount")){
			resu = createAccount((String) params.get(0), (String) params.get(1));
		} else {
			//TODO Insert a new Exception Type
			resu = new Exception("Illegal Method");
		}
		return (Serializable) resu;
	}
	
	
}
