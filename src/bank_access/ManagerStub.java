package bank_access;

import mware_lib.NameServiceReference;
import mware_lib.RemoteCaller;
import mware_lib.RemoteServiceReference;

public class ManagerStub extends ManagerImplBase {

	NameServiceReference ref;
	
	public ManagerStub(NameServiceReference ref){
		this.ref = ref;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		
		Object resu = null;
		resu = RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "createAccount", owner, branch));
		return (String)resu;
	}
}
