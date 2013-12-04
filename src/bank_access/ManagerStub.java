package bank_access;

import mware_lib.RemoteReference;

public class ManagerStub extends ManagerImplBase {

	RemoteReference ref;
	
	public ManagerStub(RemoteReference ref){
		this.ref = ref;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		// TODO Auto-generated method stub
		return null;
	}
}
