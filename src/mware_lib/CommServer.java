package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;


public class CommServer {
    private ServerSocket MySvrSocket;

    public CommServer(int listenPort) throws IOException {
        MySvrSocket = new ServerSocket(listenPort);
    }

    public CommConnection getConnection() throws IOException {
        return new CommConnection(MySvrSocket.accept());
    }

    public void shutdown() throws IOException {
        MySvrSocket.close();
    }
}