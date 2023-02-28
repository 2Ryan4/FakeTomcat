package tomcat.servlets;

import tomcat.http.RyanRequest;
import tomcat.http.RyanResponse;
import tomcat.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

public class RyanCalServlet extends RyanHttpServlet{

    @Override
    public void doGet(RyanRequest request, RyanResponse response) {
        int num1 = WebUtils.parseInt(request.getParameter("num1"), 0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"), 0);
        int sum = num1 + num2;
        OutputStream outputStream = response.getOutputStream();
        String resp = RyanResponse.respHeader + "<h1>" + num1 + " + " + num2 + " = " + sum + "</h1>";
        try {
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("失败");
        }
    }

    @Override
    public void doPost(RyanRequest request, RyanResponse response) {
        this.doGet(request, response);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
