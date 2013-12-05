package bank_access;

import java.io.Serializable;
import java.util.ArrayList;

import mware_lib.RemoteCall;
import mware_lib.NameServiceReference;

public abstract class AccountImplBase implements RemoteCall {
	public abstract void transfer(double amount) throws OverdraftException;

	public abstract double getBalance();

	public static AccountImplBase narrowCast(Object rawObjectRef) {
		
		return new AccountStub((NameServiceReference)rawObjectRef);
	}
	
	@Override
	public Serializable callMethod(String methodName, ArrayList<Object> params){
		Object resu = null;
		if(methodName.equals("transfer")){
			try {
				transfer((double)params.get(0));
				resu = null;
			} catch (OverdraftException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resu = e;
			}
		}else if(methodName.equals("getBalance")){
			resu = getBalance();
		}else{
			//TODO Insert a new Exception Type
			resu = new Exception("Illegal Method");
		}
		return (Serializable) resu;
	}
}
