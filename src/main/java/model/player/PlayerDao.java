package model.player;

import db.DBConnection;
import dto.PlayerFindResponseDto;
import util.QueryExecutionStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    private final static PlayerDao playerDAO = new PlayerDao();
    private Connection connection = DBConnection.getConnection();

    public static PlayerDao getInstance(){
        return playerDAO;
    }

    public PlayerDao() {
    }

    public QueryExecutionStatus add(int teamId, String name, Position position) {
        String query = "insert into player (team_id, name, position) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            statement.setString(2, name);
            statement.setString(3, position.name());
            statement.executeUpdate();
            return QueryExecutionStatus.SUCCESS;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return QueryExecutionStatus.FAIL;
        }
    }

    public List<PlayerFindResponseDto> findAll(int teamId) {
        List<PlayerFindResponseDto> playerList = new ArrayList<>();
        String query = "select id, name, position, created_at from player where team_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Position position = Position.findByName(resultSet.getString("position"));
                    Timestamp createdAt = resultSet.getTimestamp("created_at");

                    PlayerFindResponseDto result = PlayerFindResponseDto.builder()
                            .id(id)
                            .name(name)
                            .position(position)
                            .createdAt(createdAt)
                            .build();

                    playerList.add(result);
                }
                return playerList;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public QueryExecutionStatus updateStatus(int playerId) {
        String query = "update player set team_id = null where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            statement.executeUpdate();
            return QueryExecutionStatus.SUCCESS;
        } catch (SQLException e) {
            return QueryExecutionStatus.FAIL;
        }
    }
}
