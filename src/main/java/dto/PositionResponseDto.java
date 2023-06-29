package dto;

import lombok.Builder;
import lombok.ToString;
import model.player.Position;

import java.util.Map;

@Builder
@ToString
public class PositionResponseDto {

    private Position position;
    private Map<String, String> teamTable;
}
