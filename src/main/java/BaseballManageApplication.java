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

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Connection connection = DBConnection.getInstance();

        Set<Class<Object>> classes = componentScan("./controller");

        while (true) {
            String uri = inputUri();
            if (uri.equals("EXIT")) {
                break;
            }
            String[] parsed = uri.split("\\?");
            String url = parsed[0];
            if (hasQueryParams(uri)) {
                String queryParamURL = parsed[1];

                Map<String, String> queryParams = getQueryParams(queryParamURL);

                try {
                    findUri(classes, uri, queryParams);
                } catch (RuntimeException e) {
                    System.out.println("올바르지 않은 URL입니다.");
                }

            } else {
                try {
                    findUri(classes, url);

                } catch (RuntimeException e) {
                    System.out.println("올바르지 않은 URL입니다.");
                }
            }


            // "?" 있으면으로 수정
            // 없으면 그대로 매핑


        }


        connection.close();
    }

    private static Map<String, String> getQueryParams(String queryParams) {
        HashMap<String, String> paramMap = new HashMap<>();
        String[] queryParam = queryParams.split("&");
        for (String query : queryParam) {
            String[] parsedQuery = query.split("=");
            String key = parsedQuery[0];
            String value = parsedQuery[1];
            paramMap.put(key, value);
        }

        return paramMap;
    }

    private static boolean hasQueryParams(String uri) {
        return uri.contains("?");
    }

    private static String inputUri() {
        return scanner.nextLine();
    }

    public static void findUri(Set<Class<Object>> classes, String uri, Map<String, String> paramMap) throws Exception {
        boolean isFind = false;
        for (Class<Object> cls : classes) {
            if (cls.isAnnotationPresent(Controller.class)) {
                Object instance = cls.newInstance();
                System.out.println("instance = " + instance);
                Method[] methods = cls.getDeclaredMethods();

                for (Method mt : methods) {
                    RequestMapping anno = mt.getDeclaredAnnotation(RequestMapping.class);
                    /*
                    anno.uri() => 야구장등록
                    uri => 야구장등록?name=잠실야구징
                     */
                    if (uri.contains(anno.uri())) {
//                    if (anno.uri().equals(uri)) {
                        isFind = true;
                        mt.invoke(instance, new Object[]{paramMap});
                    }
                }
            }
        }
        if (!isFind) {
            System.out.println("404 Not Found");
        }
    }

    public static void findUri(Set<Class<Object>> classes, String uri) throws Exception {
        boolean isFind = false;
        for (Class<Object> cls : classes) {
            if (cls.isAnnotationPresent(Controller.class)) {
                Object instance = cls.newInstance();
                System.out.println("instance = " + instance);
                Method[] methods = cls.getDeclaredMethods();

                for (Method mt : methods) {
                    RequestMapping anno = mt.getDeclaredAnnotation(RequestMapping.class);
                    /*
                    anno.uri() => 야구장등록
                    uri => 야구장등록?name=잠실야구징
                     */
                    if (uri.contains(anno.uri())) {
//                    if (anno.uri().equals(uri)) {
                        isFind = true;
                        mt.invoke(instance);
                    }
                }
            }
        }
        if (!isFind) {
            System.out.println("404 Not Found");
        }
    }

    public static Set<Class<Object>> componentScan(String pkg) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Set<Class<Object>> classes = new HashSet<Class<Object>>();

        URL packageUrl = classLoader.getResource(pkg);
        File packageDirectory = new File(packageUrl.toURI());
        for (File file : Objects.requireNonNull(packageDirectory.listFiles())) {
            if (file.getName().endsWith(".class")) {
                String className = pkg.substring(2) + "." + file.getName().replace(".class", "");
                Class<Object> cls = (Class<Object>) Class.forName(className);
                classes.add(cls);
            }
        }
        return classes;
    }
}