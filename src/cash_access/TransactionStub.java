package cash_access;

import java.io.IOException;
import java.util.ArrayList;

import mware_lib.RemoteReference;
import mware_lib.networking.CommClient;

public class TransactionStub extends TransactionImplBase {
	RemoteReference ref;
	
	TransactionStub(RemoteReference ref){
		this.ref = ref;
	}
	@Override
	public void deposit(String accountID, double amount)
			throws InvalidParamException {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add((Object)amount);
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			client.callMethod("deposit", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void withdraw(String accountID, double amount)
			throws InvalidParamException, OverdraftException {
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add((Object)amount);
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			client.callMethod("withdraw", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public double getBalance(String accountID) throws InvalidParamException {
		Object resu;
		ArrayList<Object> params = new ArrayList<Object>();
		params.add((Object)accountID);
		try {
			CommClient client = new CommClient(ref.getHost(), ref.getPort());
			resu = client.callMethod("getBalance", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resu = 0;
		}
		return (double)resu;
	}
	
}
