package model.stadium;

import db.DBConnection;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Getter
public class StadiumDao {
    private Connection connection = DBConnection.getInstance();

    public boolean add(String name) {
        String query = "insert into stadium (name) values(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("잘들어감");
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

//    public List<Stadium> getList() {
//    }
}
