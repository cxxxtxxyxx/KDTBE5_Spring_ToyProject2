package model.team;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
