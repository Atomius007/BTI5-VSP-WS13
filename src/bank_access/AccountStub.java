package bank_access;

import java.io.IOException;
import java.util.ArrayList;

import mware_lib.RemoteReference;
import mware_lib.networking.CommClient;

public class AccountStub extends AccountImplBase {

	RemoteReference ref;
	
	AccountStub(RemoteReference ref){
		this.ref = ref;
	}
	@Override
	public void transfer(double amount) throws OverdraftException {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add((Object)amount);
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			client.callMethod("transfer", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public double getBalance() {
		Object resu = null;
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			resu = client.callMethod("getBalance", null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return (double)resu;
	}

}
