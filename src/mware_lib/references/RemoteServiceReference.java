package mware_lib.references;

import java.io.Serializable;
import java.util.ArrayList;

public class RemoteServiceReference implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2263006073807023563L;
	/**
	 * @param args
	 */
	private String objectName;
	private String methodName;
	private ArrayList<Object> arguments;

	public RemoteServiceReference(String objectName, String methodName, Object ... args){
		this.objectName = objectName;
        this.methodName = methodName;
		
        arguments = new ArrayList<Object>();
		for(Object o: args){
			arguments.add(o);
		}
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objName) {
		this.objectName = objName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String method) {
		this.methodName = method;
	}
	
	public ArrayList<Object> getArguments(){
		return arguments;
	}
	
	public String toString(){
		  return String.format("NameServiceReference %s.%s - %s", objectName, methodName, arguments.toString());
	}
}
