package tomcat.handler;

import tomcat.TomcatV3;
import tomcat.http.RyanRequest;
import tomcat.http.RyanResponse;
import tomcat.servlets.RyanCalServlet;
import tomcat.servlets.RyanHttpServlet;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{
    private Socket socket = null;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//            System.out.println("TomcatV2接受到数据如下");
//            System.out.println("当前线程" + Thread.currentThread().getName());
//            String mes = null;
//            while ((mes = bufferedReader.readLine()) != null) {
//                if (mes.length() == 0) {
//                    break;
//                }
//                System.out.println(mes);
//            }
            RyanRequest ryanRequest = new RyanRequest(inputStream);
//            String num1 = ryanRequest.getParameter("num1");
//            String num2 = ryanRequest.getParameter("num2");
//            System.out.println(num1 + " " + num2);
            RyanResponse ryanResponse = new RyanResponse(socket.getOutputStream());
//            String resp = RyanResponse.respHeader + "<h1>hi,V2 test success</h1>";
//            OutputStream outputStream = ryanResponse.getOutputStream();
//            outputStream.write(resp.getBytes());
//            outputStream.flush();
//            outputStream.close();
//            RyanCalServlet ryanCalServlet = new RyanCalServlet();
//            ryanCalServlet.doGet(ryanRequest, ryanResponse);

            String uri = ryanRequest.getUri();
            String servletName = TomcatV3.urlMap.get(uri);
            RyanHttpServlet ryanHttpServlet = TomcatV3.servletMap.get(servletName);
            if (ryanHttpServlet != null) {
                ryanHttpServlet.service(ryanRequest, ryanResponse);
            } else {
                String res = RyanResponse.respHeader + "<h1>404 NOT FOUND</h1>";
                OutputStream outputStream = ryanResponse.getOutputStream();
                outputStream.write(res.getBytes());
                outputStream.flush();
                outputStream.close();
            }

//            String resHeader = "HTTP/1.1 200 OK\r\n" +
//                    "Content-Type: text/html;charset=utf-8\r\n\r\n";
//            String res = resHeader + "<h1>hi,V2 test success</h1>";
//            OutputStream outputStream = socket.getOutputStream();

//            System.out.println(res);
//            outputStream.write(res.getBytes());
//
//            outputStream.flush();
//            outputStream.close();
//            bufferedReader.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
