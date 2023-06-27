package db;

import org.h2.tools.Server;

import java.sql.*;

public class DBConnection {

    private static Connection connection = getInstance();

    public static Connection getConnection() {
        return connection;
    }

    private static Connection getInstance() {
        // MySQL 연결 정보
        String url = "jdbc:h2:~/test;MODE=MYSQL";
        String username = "sa";
        String password = "";

        // JDBC 드라이버 로드
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            initTable(connection);
            System.out.println("DB와 연결되었습니다!");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void initTable(Connection connection) throws SQLException {
        dropTable(connection);
        // stadium 테이블 생성
        createTable(connection);

    }

    private static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createStadiumTable = "\n" +
                "CREATE TABLE IF NOT EXISTS stadium  (\n" +
                "  id int NOT NULL AUTO_INCREMENT,\n" +
                "  name varchar(100) NOT NULL,\n" +
                "  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (id)\n" +
                ");";

        statement.executeUpdate(createStadiumTable);

        statement = connection.createStatement();
        String createTeamTable = "CREATE TABLE IF NOT EXISTS `team` (\n" +
                "     `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "     `stadium_id` INT UNSIGNED NOT NULL,\n" +
                "     `name` VARCHAR(20) NOT NULL,\n" +
                "    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    UNIQUE INDEX `id_UNIQUE` (`id` ASC),\n" +
                "    UNIQUE INDEX `name_UNIQUE` (`name` ASC),\n" +
                "    FOREIGN KEY (`stadium_id`) REFERENCES `stadium` (`id`)\n" +
                ")";
//                "    INDEX `fk_stadium_team_idx_idx` (`stadium_id` ASC) VISIBLE,\n" +
//                "    CONSTRAINT `fk_stadium_team_idx`\n" +

//                "    \n" +
//                "    ON DELETE NO ACTION\n" +

        statement.executeUpdate(createTeamTable);


        statement = connection.createStatement();
        String createPlayerTable = "CREATE TABLE IF NOT EXISTS `player` (\n" +
                "       `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "       `team_id` INT UNSIGNED NOT NULL,\n" +
                "       `name` VARCHAR(20) NOT NULL,\n" +
                "        `position` VARCHAR(45) NOT NULL,\n" +
                "        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "\n" +
                "        INDEX `fk_team_player_idx` (`team_id` ASC),\n" +
                "        UNIQUE INDEX `uk_team_id_position_idx` (`team_id` ASC, `position` ASC),\n" +
                "        CONSTRAINT `fk_team_player_idx`\n" +
                "        FOREIGN KEY (`team_id`)\n" +
                "        REFERENCES `team` (`id`)\n" +
                "        ON DELETE NO ACTION\n" +
                "        ON UPDATE NO ACTION\n" +
                "    )";
        statement.executeUpdate(createPlayerTable);

        statement = connection.createStatement();
        String createOutPlayerTable = "CREATE TABLE IF NOT EXISTS `out_player` (\n" +
                "   `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "   `player_id` INT UNSIGNED NOT NULL,\n" +
                "   `reason` VARCHAR(200) NOT NULL,\n" +
                "    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    INDEX `fk_out_player_player_idx` (`player_id` ASC),\n" +
                "    CONSTRAINT `fk_out_player_player`\n" +
                "    FOREIGN KEY (`player_id`)\n" +
                "    REFERENCES `player` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION)";
        statement.executeUpdate(createOutPlayerTable);

    }

    private static void dropTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();


        statement = connection.createStatement();
        String dropOutPlayerTable = "DROP TABLE if exists out_player";
        statement.executeUpdate(dropOutPlayerTable);

        statement = connection.createStatement();
        String dropPlayerTable = "DROP TABLE if exists player";
        statement.executeUpdate(dropPlayerTable);

        String dropTeamTable = "DROP TABLE if exists team";
        statement.executeUpdate(dropTeamTable);


        statement = connection.createStatement();
        String dropStadiumTable = "DROP TABLE if exists stadium";
        statement.executeUpdate(dropStadiumTable);






    }
}
