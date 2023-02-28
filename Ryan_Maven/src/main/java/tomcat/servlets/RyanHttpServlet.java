package tomcat.servlets;

import tomcat.http.RyanRequest;
import tomcat.http.RyanResponse;

import java.io.IOException;

public abstract class RyanHttpServlet implements RyanServlet{
    @Override
    public void service(RyanRequest request, RyanResponse response) throws IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request, response);
        } else if ("Post".equalsIgnoreCase(request.getMethod())) {
            this.doPost(request, response);
        }
    }

    public abstract void doGet(RyanRequest request, RyanResponse response);
    public abstract void doPost(RyanRequest request, RyanResponse response);
}
