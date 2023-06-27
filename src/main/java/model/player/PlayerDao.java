package model.player;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDao {
    private Connection connection = DBConnection.getConnection();

    public boolean add(int teamId, String name, Position position) {
        String query = "insert into player (team_id, name, position) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            statement.setString(2, name);
            statement.setString(3, position.name());
            statement.executeUpdate();
            System.out.println("Player 잘들어감");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
