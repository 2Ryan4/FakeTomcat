package tomcat;

import tomcat.handler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TomcatV2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("V2正在监听");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            RequestHandler rh = new RequestHandler(socket);
            new Thread(rh).start();
        }
    }
}
