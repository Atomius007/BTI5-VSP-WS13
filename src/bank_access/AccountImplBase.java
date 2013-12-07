package bank_access;

import java.util.ArrayList;
import mware_lib.references.NameServiceReference;

public abstract class AccountImplBase implements AccountSkel{
	public abstract void transfer(double amount) throws OverdraftException;

	public abstract double getBalance();

	public static AccountImplBase narrowCast(Object rawObjectRef) {
		
		AccountStub stub = new AccountStub((NameServiceReference) rawObjectRef);
		return stub;
	}
	
	 @Override
     public Object callMethod(String name, ArrayList<Object> params) {
             
             if(name.equals("transfer")){
                     try {
                    	 transfer((double)params.get(0)); return null;
                     } catch (OverdraftException e) { 
                    	 e.printStackTrace(); return e;
                     }
             }else if(name.equals("getBalance")){
                     return getBalance();
             }else{
                     return new IllegalArgumentException("Only methods 'void transfer(double amount)' and 'double getBalance()' supported by AccountImplBase)");
             }
     }
	
	}


