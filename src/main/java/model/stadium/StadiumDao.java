package model.stadium;

import db.DBConnection;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StadiumDao {
    private Connection connection = DBConnection.getConnection();

    public boolean add(String name) {
        String query = "insert into stadium (name) values(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("잘들어감");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Stadium> findAll() {
        List<Stadium> stadiumList = new ArrayList<>();
        String query = "select * from stadium";

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");

                    Stadium stadium = Stadium.builder()
                            .id(id)
                            .name(name)
                            .createdAt(createdAt)
                            .build();

                    stadiumList.add(stadium);
                }

                return stadiumList;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
