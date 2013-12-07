package bank_access;

import java.util.ArrayList;

import mware_lib.*;

public abstract class ManagerImplBase implements ManagerSkel{
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		ManagerStub stub = new ManagerStub((NameServiceReference) rawObjectRef);
		return stub;
		
		
	}
	 @Override
     public Object callMethod(String name, ArrayList<Object> params) {
             if(name.equals("createAccount")){
                     return createAccount((String)params.get(0), (String)params.get(1));
             }else{
                     return new IllegalArgumentException("Only method 'String createAccount(String owner, String branch)' supported by ManagerImplBase)");
             }
             //return null;
     }


}