package testapp;

import mware_lib.*;
import bank_access.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ManagerStub m = null;
		ObjectBroker objBroker = ObjectBroker.init("127.0.0.1", 7777);
		NameService nameSvc = objBroker.getNameService();
		nameSvc.rebind((Object) m, "Test123");
		System.out.println("Bound!");
		while(true);

	}

}
