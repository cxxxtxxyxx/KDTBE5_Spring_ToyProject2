package model.team;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Team {

    private int id;
    private int stadiumId;
    private String name;
    private Timestamp createdAt;
}
