package bank_access;

import mware_lib.NameServiceReference;

public abstract class AccountImplBase{
	public abstract void transfer(double amount) throws OverdraftException;

	public abstract double getBalance();

	public static AccountImplBase narrowCast(Object rawObjectRef) {
		
		return new AccountStub((NameServiceReference)rawObjectRef);
	}
	
	}
