import db.DBConnection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Connection;

public class BaseballManageApplication {
    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();

    }
}