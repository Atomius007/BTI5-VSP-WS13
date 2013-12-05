package bank_access;

import mware_lib.*;

public abstract class ManagerImplBase {
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		ManagerStub stub = new ManagerStub((NameServiceReference)rawObjectRef); 
			return stub;
		}
}
