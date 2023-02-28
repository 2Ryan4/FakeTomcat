package tomcat.utils;

public class WebUtils {
    public static int parseInt(String strNum, int num) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + "不能转成数字");
        }
        return num;
    }
}
