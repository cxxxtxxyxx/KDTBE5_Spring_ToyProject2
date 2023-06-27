package model.outplayer;


import lombok.Builder;
import lombok.ToString;
import model.player.Position;

import java.sql.Timestamp;

@Builder
@ToString
public class OutPlayerResponseDto {

    private String name;
    private Position position;
    private String reason;
    private Timestamp createdAt;

}
