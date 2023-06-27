package model.outplayer;

import db.DBConnection;
import model.player.PlayerFindResponseDto;
import model.player.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutPlayerDao {
    private Connection connection = DBConnection.getConnection();

    public boolean add(int playerId, String reason) {
        String query = "insert into out_player (player_id, reason) values (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            statement.setString(2, reason);
            statement.executeUpdate();
            System.out.println("out Player 잘들어감");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<OutPlayerResponseDto> findAllJoinPlayer() {
        List<OutPlayerResponseDto> outPlayerResponseDtoList = new ArrayList<>();
        String query = "select p.name, p.position, op.reason, op.created_at from player p left join out_player op on op.player_id = p.id";


        try (PreparedStatement statement = connection.prepareStatement(query)) {


            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    Position position = Position.findByName(resultSet.getString("position"));
                    String reason = resultSet.getString("reason");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");

                    OutPlayerResponseDto result = OutPlayerResponseDto.builder()
                            .name(name)
                            .reason(reason)
                            .position(position)
                            .createdAt(createdAt)
                            .build();

                    outPlayerResponseDtoList.add(result);
                }

                return outPlayerResponseDtoList;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
