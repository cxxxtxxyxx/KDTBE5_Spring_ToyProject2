import db.DBConnection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Connection;
import java.sql.SQLException;

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
}