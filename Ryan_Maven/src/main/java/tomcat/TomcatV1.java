package tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TomcatV1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("正在监听8081端口");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            String mes = null;
            while ((mes = bufferedReader.readLine()) != null) {
                if (mes.length() == 0) {
                    break;
                }
                System.out.println(mes);
            }
            String resHeader = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n\r\n";

            String res = resHeader + "hi,V1 test success";
            OutputStream outputStream = socket.getOutputStream();
            System.out.println();
            System.out.println(res);
            outputStream.write(res.getBytes());

            outputStream.flush();
            outputStream.close();
            bufferedReader.close();
            socket.close();
        }


    }
}
