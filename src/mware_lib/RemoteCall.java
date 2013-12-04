package mware_lib;

import java.io.Serializable;

public interface RemoteCall {
	public abstract Serializable callMethod(String methodName, Serializable[] params);

}
