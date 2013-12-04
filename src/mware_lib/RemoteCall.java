package mware_lib;

import java.io.Serializable;
import java.util.ArrayList;

public interface RemoteCall {
	public Serializable callMethod(String methodName, ArrayList<Object> params);

}
