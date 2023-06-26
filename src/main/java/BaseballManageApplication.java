import db.DBConnection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseballManageApplication {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getInstance();




        connection.close();
    }
}