package model.team;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {

    private Connection connection = DBConnection.getConnection();

    // TODO stadiumDao.findById로 Stadium이 존재할 때만 로직 실행
    // 없으면 예외 처리
    public boolean add(int stadiumId, String name) {
        String query = "insert into team (stadium_id, name) values(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stadiumId);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("팀 잘들어감");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Team findById(int teamId) {
        String query = "select * from team where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int stadiumId = resultSet.getInt("stadium_id");
                    String name = resultSet.getString("name");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");

                    System.out.println("팀 조회 성공");
                    return Team.builder()
                            .id(id)
                            .name(name)
                            .stadiumId(stadiumId)
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

    public List<TeamResponseDto> findAllJoinStadium() {

        List<TeamResponseDto> teamResponseDtoList = new ArrayList<>();
        String query = "select t.id as teamId, t.name as teamName, t.created_at as teamCreatedAt, s.name as stadiumName from stadium s join team t on t.stadium_id = s.id";

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int teamId = resultSet.getInt("teamId");
                    String teamName = resultSet.getString("teamName");
                    Timestamp createdAt = resultSet.getTimestamp("teamCreatedAt");
                    String stadiumName = resultSet.getString("stadiumName");

                    TeamResponseDto teamResponseDto = TeamResponseDto.builder()
                            .teamId(teamId)
                            .teamName(teamName)
                            .teamCreatedAt(createdAt)
                            .stadiumName(stadiumName)
                            .build();

                    teamResponseDtoList.add(teamResponseDto);
                }

                return teamResponseDtoList;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
