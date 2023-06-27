package model.player;

import lombok.Builder;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@ToString
public class Player {
    private int id;
    private int teamId;
    private String name;
    private Position position;
    private Timestamp createdAt;

}
