package bank_access;

import mware_lib.NameServiceReference;
import mware_lib.RemoteCaller;
import mware_lib.RemoteServiceReference;

public class AccountStub extends AccountImplBase {

	NameServiceReference ref;
	
	AccountStub(NameServiceReference ref){
		this.ref = ref;
	}
	@Override
	public void transfer(double amount) throws OverdraftException {
		RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "transfer", amount));

		
		/* OLD CODE FIRST DRAFT
		 ArrayList<Object> params = new ArrayList<Object>();
		 
		params.add((Object)amount);
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			client.callMethod("transfer", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	@Override
	public double getBalance() {
		Object resu = null;
		resu = RemoteCaller.callMethod(ref, new RemoteServiceReference(ref.getname(), "getBalance"));
		return (double)resu;
	}

}
