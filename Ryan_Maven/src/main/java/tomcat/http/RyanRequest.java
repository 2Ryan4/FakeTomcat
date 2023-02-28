package tomcat.http;

import java.io.*;
import java.util.HashMap;

public class RyanRequest {
    private String method;
    private String uri;
    private HashMap<String, String> parameterMap = new HashMap<>();
    private InputStream inputStream = null;

    public RyanRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
//        inputStream.close();
//        bufferedReader.close();
        encapHttpRequest();

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getParameter(String name) {
        if (parameterMap.containsKey(name)) {
            return parameterMap.get(name);
        } else {
            return "";
        }
    }

    public void encapHttpRequest() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String requestLine = bufferedReader.readLine();//读取第一行
        String[] requestLineArr = requestLine.split(" ");//按空格分隔
        method = requestLineArr[0];//获取请求方式
        int index = requestLineArr[1].indexOf("?");//判断是否有参数列表
        if (index == -1) {
            uri = requestLineArr[1];
        } else {
            uri = requestLineArr[1].substring(0, index);//获取url
            String parameters = requestLineArr[1].substring(index + 1);//获取参数列表
            String[] parametersPair = parameters.split("&");
            if (parametersPair != null && !"".equals(parametersPair)) {
                for (String parameterPair : parametersPair) {
                    String[] parameterVal = parameterPair.split("=");
                    if (parameterVal.length == 2) {
                        parameterMap.put(parameterVal[0], parameterVal[1]);
                    }
                }

            }
        }
    }
}
