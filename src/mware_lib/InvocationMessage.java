package mware_lib;

import java.io.Serializable;

public class InvocationMessage implements RemoteMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1006893139011669392L;
	private String methodName;
	private Object[] params;
	
	public InvocationMessage(String methodName, Serializable[] params){
		this.setMethodName(methodName);
		this.setParams(params);
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
