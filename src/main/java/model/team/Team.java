package model.team;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class Team {
    private int id;
    private int stadiumId;
    private String name;
    private Timestamp createdAt;
}
