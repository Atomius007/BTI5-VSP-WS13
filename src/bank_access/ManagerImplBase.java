package bank_access;

import mware_lib.*;

public abstract class ManagerImplBase implements RemoteCall {
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		RemoteReference ref = (RemoteReference) rawObjectRef; 
		return null;
		}
}
