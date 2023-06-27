package model.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@ToString
public class PlayerFindResponseDto {
    private int id;
    private String name;
    private Position position;
    private Timestamp createdAt;

}
