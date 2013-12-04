package bank_access;

import java.io.IOException;
import java.util.ArrayList;

import mware_lib.RemoteReference;
import mware_lib.networking.CommClient;

public class ManagerStub extends ManagerImplBase {

	RemoteReference ref;
	
	public ManagerStub(RemoteReference ref){
		this.ref = ref;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add((Object)owner);
		params.add((Object)branch);
		Object resu = null;
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			resu = client.callMethod("createAccount", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String)resu;
	}
}
