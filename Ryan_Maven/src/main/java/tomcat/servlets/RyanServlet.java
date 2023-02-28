package tomcat.servlets;


import tomcat.http.RyanRequest;
import tomcat.http.RyanResponse;

import java.io.IOException;

public interface RyanServlet {
    void init() throws Exception;

    void service(RyanRequest request, RyanResponse response) throws IOException;

    void destroy();

}
