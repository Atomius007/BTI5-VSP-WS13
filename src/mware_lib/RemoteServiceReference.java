package mware_lib;

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
	private String objName;
	private String method;
	private ArrayList<Object> params;

	public RemoteServiceReference(String objName, String method, Object ... parameters){
		this.objName = objName;
        this.method = method;
		params = new ArrayList<Object>();
		for(Object o: parameters){
			params.add(o);
		}
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public ArrayList<Object> getParams(){
		return params;
	}
	
	public String toString(){
		  return String.format("NameServiceReference ['%s.%s', %s]", objName, method, params.toString());
	}
}
