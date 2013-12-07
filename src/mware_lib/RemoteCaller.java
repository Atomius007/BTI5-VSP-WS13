package mware_lib;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import mware_lib.networking.*;
import mware_lib.references.NameServiceReference;
import mware_lib.references.RemoteServiceReference;

public class RemoteCaller {
	
	public static Object callMethod(NameServiceReference ns, RemoteServiceReference rs){
		System.out.println("callMethod - Remote Caller: ns="+ns+" rs="+rs.toString());
		CommConnection conn = null;
		Object resu = null;
	
		try {
			System.out.println("Connecting: "+ ns.getHost()+":"+ns.getPort());
			conn = new CommConnection(new Socket(ns.getHost(), ns.getPort()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("send rs=" + rs.toString() + " to " +ns.getHost()+":"+ns.getPort());
		conn.send(rs);
		
		
		try {
			System.out.println("receive");
			resu = conn.receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resu;
	

	}
}	