package dto;


import lombok.Builder;
import lombok.ToString;
import model.outplayer.Reason;
import model.player.Position;

import java.sql.Timestamp;

@Builder
@ToString
public class OutPlayerResponseDto {

    private String name;
    private Position position;
    private Reason reason;
    private Timestamp createdAt;
}
