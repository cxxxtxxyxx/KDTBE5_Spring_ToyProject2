package db;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getInstance() {
        // MySQL 연결 정보
        String url = "jdbc:h2:~/test";
        String username = "sa";
        String password = "";

        // JDBC 드라이버 로드
        try {
//            Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists").start();
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("디버그 : DB연결 성공");

//            server.stop();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
