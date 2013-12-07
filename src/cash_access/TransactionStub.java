package cash_access;

import mware_lib.RemoteCaller;
import mware_lib.references.NameServiceReference;
import mware_lib.references.RemoteServiceReference;

public class TransactionStub extends TransactionImplBase {
	NameServiceReference ref;
	
	TransactionStub(NameServiceReference ref){
		this.ref = ref;
	}
	@Override
	public void deposit(String accountID, double amount)
			throws InvalidParamException {

		System.out.println("deposit - STUB");
		System.out.println("callMethod - STUB " +ref +" ");
		RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "deposit", accountID, amount));		
	}

	@Override
	public void withdraw(String accountID, double amount)
			throws InvalidParamException, OverdraftException {
		System.out.println("withdraw - STUB");
		System.out.println("callMethod - STUB " +ref +" ");
		RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "withdraw", accountID, amount));	
	}

	@Override
	public double getBalance(String accountID) throws InvalidParamException {
		System.out.println("getBalance - STUB");
		System.out.println("callMethod - STUB " +ref +" ");
		Object resu = null;
		resu = RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "getBalance", accountID));
		return (double)resu;
	}
	
}
