package mware_lib;

import java.io.Serializable;

public class NameServiceReference implements Serializable {

	private static final long serialVersionUID = 903035577709764592L;
    private String host;
    private int port;
    private String name;
    
   
    public NameServiceReference(String host, int port, String name) {
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

    public String getname() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("NameServiceReference ['%s:%d', %s]", host, port, name);
    }
    
    public void setname(String name) {
        this.name = name;
    }

}
