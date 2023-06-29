package model.stadium;

import db.DBConnection;
import lombok.Getter;
import util.QueryExecutionStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StadiumDao {
    private final static StadiumDao stadiumDao = new StadiumDao();
    private Connection connection = DBConnection.getConnection();

    public static StadiumDao getInstance(){
        return stadiumDao;
    }

    public StadiumDao() {
    }

    public QueryExecutionStatus add(String name) {
        String query = "insert into stadium (name) values(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
            return QueryExecutionStatus.SUCCESS;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return QueryExecutionStatus.FAIL;
        }
    }

    public Stadium findById(int stadiumId){
        String query = "select * from stadium where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stadiumId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");

                    return Stadium.builder()
                            .id(id)
                            .name(name)
                            .createdAt(createdAt)
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
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
