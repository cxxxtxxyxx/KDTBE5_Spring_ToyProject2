import core.Controller;
import core.RequestMapping;
import db.DBConnection;
import util.ApplicationSymbol;
import util.ErrorMessage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

public class BaseballManageApplication {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        run();
    }

    private static void run() throws Exception {
        Connection connection = DBConnection.getConnection();
        Set<Class<Object>> classes = componentScan(ApplicationSymbol.START_PACKAGE_POINT);

        while (true) {
            String uri = inputUri();
            if (uri.equals(ApplicationSymbol.EXIT_COMMAND)) {
                break;
            }

            Map<String, String> queryParams = getQueryParamMap(uri);

            try {
                findUri(classes, uri, queryParams);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                System.out.println(ErrorMessage.NOT_FOUND);
            }
        }

        connection.close();
    }

    private static String[] getParsedUri(String uri) {
        return uri.split("\\?");
    }

    private static Map<String, String> getQueryParamMap(String uri) {
        if (hasQueryParams(uri)) {
            String queryParamURL = getParsedUri(uri)[1];
            return parsedQueryParam(queryParamURL);
        }

        return null;
    }

    private static Map<String, String> parsedQueryParam(String queryParams) {
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

    private static void findUri(Set<Class<Object>> classes, String uri, Map<String, String> paramMap) throws Exception {
        boolean isFind = false;
        for (Class<Object> cls : classes) {
            if (cls.isAnnotationPresent(Controller.class)) {
                Method getInstance = cls.getMethod(ApplicationSymbol.SINGLETON_METHOD_NAME);
                Object instance = getInstance.invoke(null);
                Method[] methods = cls.getDeclaredMethods();

                for (Method mt : methods) {
                    RequestMapping anno = mt.getDeclaredAnnotation(RequestMapping.class);
                    if (anno == null) {
                        continue;
                    }

                    if (uri.contains(anno.uri())) {
                        isFind = true;
                        if (paramMap == null) {
                            mt.invoke(instance);
                            continue;
                        }
                        mt.invoke(instance, new Object[] { paramMap });
                    }
                }
            }
        }
        if (!isFind) {
            System.out.println(ErrorMessage.NOT_FOUND);
        }
    }

    private static Set<Class<Object>> componentScan(String pkg) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return getComponentClasses(pkg, classLoader);
    }

    private static Set<Class<Object>> getComponentClasses(String pkg, ClassLoader classLoader) throws URISyntaxException, ClassNotFoundException {
        Set<Class<Object>> classes = new HashSet<Class<Object>>();
        URL packageUrl = classLoader.getResource(pkg);
        File packageDirectory = new File(packageUrl.toURI());
        for (File file : Objects.requireNonNull(packageDirectory.listFiles())) {
            if (file.getName().endsWith(".class")) {
                String className = getClassName(pkg, file);
                Class<Object> cls = (Class<Object>) Class.forName(className);
                classes.add(cls);
            }
        }

        return classes;
    }

    private static String getClassName(String pkg, File file) {
        return pkg.substring(2) + "." + file.getName().replace(".class", "");
    }
}