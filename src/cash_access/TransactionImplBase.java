package cash_access;

import java.io.Serializable;
import java.util.ArrayList;

import mware_lib.NameServiceReference;

public abstract class TransactionImplBase implements TransactionSkel{
	public abstract void deposit(String accountID, double amount)
			throws InvalidParamException;

	public abstract void withdraw(String accountID, double amount)
			throws InvalidParamException, OverdraftException;

	public abstract double getBalance(String accountID)
			throws InvalidParamException;

	public static TransactionImplBase narrowCast(Object rawObjectRef) {

	TransactionImplBase resu = new TransactionStub((NameServiceReference) rawObjectRef);
	return resu;
	}
	
	@Override
	public Serializable callMethod(String methodName, ArrayList<Object> params){
		Object resu = null;
		if(methodName.equals("deposit")){
			try {
				deposit((String) params.get(0), (double) params.get(1));
			} catch (InvalidParamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resu = e;
			}
		} else if(methodName.equals("withdraw")){
			try {
				withdraw((String) params.get(0), (double) params.get(1));
			} catch (InvalidParamException | OverdraftException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resu = e;
			}
		} else if(methodName.equals("getBalance")){
			try {
				resu = getBalance((String) params.get(0));
			} catch (InvalidParamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resu = e;
			}
		} else {
			//TODO Insert a new Exception Type
			resu = new Exception("Illegal Method");
		}
		return (Serializable) resu;
	}
	
	
}
