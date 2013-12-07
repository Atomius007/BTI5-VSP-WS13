package bank_access;

import mware_lib.RemoteCaller;
import mware_lib.misc.Constants;
import mware_lib.references.NameServiceReference;
import mware_lib.references.RemoteServiceReference;

public class AccountStub extends AccountImplBase {

	NameServiceReference ref;
	boolean debug = Constants.getDebug();
	
	AccountStub(NameServiceReference ref){
		this.ref = ref;
	}
	@Override
	public void transfer(double amount) throws OverdraftException {
		RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "transfer", amount));
	}

	@Override
	public double getBalance() {
		Object resu = null;
		resu = RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "getBalance"));
		return (double)resu;
	}

}
