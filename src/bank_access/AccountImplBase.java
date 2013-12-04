package bank_access;

import java.io.Serializable;
import java.util.ArrayList;

import mware_lib.RemoteCall;
import mware_lib.RemoteReference;

public abstract class AccountImplBase implements RemoteCall {
	public abstract void transfer(double amount) throws OverdraftException;

	public abstract double getBalance();

	public static AccountImplBase narrowCast(Object rawObjectRef) {
		
		return new AccountStub((RemoteReference)rawObjectRef);
	}
	
	@Override
	public Serializable callMethod(String methodName, ArrayList<Object> params){
		if(methodName.equals("transfer")){
			
		}
		return params;
		
	}
}
