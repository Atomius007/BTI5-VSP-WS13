package name_service;

import java.io.Serializable;

public class RemoteReference implements Serializable {

	private static final long serialVersionUID = 903035577709764592L;
    private String host, name;
    private int port;
    
    
   
    public RemoteReference(String host, int port, String name) {
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("RemoteReference ['%s:%d', %s]", host, port, name);
    }

}
