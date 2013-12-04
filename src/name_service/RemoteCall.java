package name_service;

import java.io.IOException;
import java.io.Serializable;

public interface RemoteCall {
	public abstract Serializable callMethod(String methodName, Serializable params) throws IOException, ClassNotFoundException, NoRemoteMessageException;
}
