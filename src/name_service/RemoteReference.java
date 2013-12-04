package name_service;

import java.io.Serializable;

public class RemoteReference implements Serializable {

	private static final long serialVersionUID = 903035577709764592L;
    private String host;
    private int port;
    private Class<?> type;
    
   
    public RemoteReference(String host, int port, Class<?extends Object> type) {
        this.host = host;
        this.port = port;
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Class<? extends Object> getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("RemoteReference ['%s:%d', %s]", host, port, type);
    }

}
