import core.Controller;
import core.RequestMapping;
import db.DBConnection;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

public class BaseballManageApplication {
    public static void main(String[] args) throws Exception {
        Connection connection = DBConnection.getInstance();

        Scanner scanner = new Scanner(System.in);
        String uri = scanner.nextLine();

        String[] parsed = uri.split("\\?");

        if (parsed.length > 2) {
            String url = parsed[0];
            String queryParams = parsed[1];

            HashMap<String, String> paramMap = new HashMap<>();
            String[] queryParam = queryParams.split("&");
            for (String query : queryParam) {
                String[] parsedQuery = query.split("=");
                String key = parsedQuery[0];
                String value = parsedQuery[1];
                paramMap.put(key, value);
            }
            System.out.println("paramMap = " + paramMap);
        }


        Set<Class> classes = componentScan("./controller");
        findUri(classes, uri);

        connection.close();
    }





    public static Set<Class> componentScan(String pkg) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("classLoader = " + classLoader);
        Set<Class> classes = new HashSet<>();

        URL packageUrl = classLoader.getResource(pkg);
        System.out.println("packageUrl = " + packageUrl);
        File packageDirectory = new File(packageUrl.toURI());
        for (File file : packageDirectory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = pkg.substring(2) + "." + file.getName().replace(".class", "");
                System.out.println(className);
                Class cls = Class.forName(className);
                classes.add(cls);
            }
        }
        return classes;
    }
}