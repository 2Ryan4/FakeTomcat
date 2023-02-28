package tomcat.http;

import java.io.OutputStream;

public class RyanResponse {
    private OutputStream outputStream = null;

    public RyanResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public static final String respHeader = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html;charset=utf-8\r\n\r\n";


}
