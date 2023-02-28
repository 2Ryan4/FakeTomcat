package tomcat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import tomcat.handler.RequestHandler;
import tomcat.servlets.RyanHttpServlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TomcatV3 {
    public static final HashMap<String, RyanHttpServlet> servletMap = new HashMap<>();
    public static final HashMap<String, String> urlMap = new HashMap<>();

    public void init() {
        String path = TomcatV3.class.getResource("/").getPath();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new File(path + "web.xml"));
//            System.out.println(document);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                if("servlet".equalsIgnoreCase(element.getName())) {
//                    System.out.println(element);
                    //使用反射,将实例放入到servletMap
                    Element name = element.element("servlet-name");
                    Element classPath = element.element("servlet-class");
                    servletMap.put(name.getText(), (RyanHttpServlet) Class.forName(classPath.getText()).getDeclaredConstructor().newInstance());
                } else if ("servlet-mapping".equalsIgnoreCase(element.getName())) {
//                    System.out.println(element);
                    Element name = element.element("servlet-name");
                    Element urlPattern = element.element("url-pattern");
                    urlMap.put(urlPattern.getText(), name.getText());
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
//        System.out.println(servletMap);
//        System.out.println(urlMap);


    }

    public static void main(String[] args) {
        TomcatV3 tomcatV3 = new TomcatV3();
        tomcatV3.init();
        tomcatV3.run();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("V3正在8081监听");
    }
}
