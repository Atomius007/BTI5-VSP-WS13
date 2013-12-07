package bank_access;


import mware_lib.*;
import mware_lib.misc.Constants;
import mware_lib.references.NameServiceReference;
import mware_lib.references.RemoteServiceReference;

public class ManagerStub extends ManagerImplBase {

	NameServiceReference ref;
	boolean debug = Constants.getDebug();
	
	public ManagerStub(NameServiceReference ref){
		this.ref = ref;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		if(debug)System.out.println("createAccount - STUB");
		Object resu = null;
		if(debug)System.out.println("callMethod - STUB " +ref +" ");
		resu = RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "createAccount", owner, branch));
		return (String)resu;
	}

}
