package mware_lib;

import java.util.ArrayList;

public class InvocationMessage implements RemoteMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1006893139011669392L;
	private String methodName;
	private ArrayList<Object> params;
	
	public InvocationMessage(String methodName, ArrayList<Object> params){
		this.setMethodName(methodName);
		this.setParams(params);
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ArrayList<Object> getParams() {
		return params;
	}

	public void setParams(ArrayList<Object> params) {
		this.params = params;
	}

}
