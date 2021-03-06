package testapp;

import mware_lib.*;
import bank_access.*;

public class TestZwo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectBroker objBroker = ObjectBroker.init("localhost", 7777);
		NameService nameSvc = objBroker.getNameService();
		Object rawObjRef = nameSvc.resolve("Test123"); //generische Referenz
		System.out.println("resolved: " + rawObjRef);
		ManagerImplBase manager = ManagerImplBase.narrowCast(rawObjRef);
		//liefert spezialisiertes Stellvertreterobjekt
		manager.createAccount("Ich", "Neuer");
		objBroker.shutDown();

	}

}
