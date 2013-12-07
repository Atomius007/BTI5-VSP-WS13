package mware_lib;

import java.util.ArrayList;

public interface RemoteCall {
	public Object callMethod(String methodName, ArrayList<Object> params);

}
