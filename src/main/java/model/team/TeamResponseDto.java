package model.team;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@ToString
public class TeamResponseDto {

    int teamId;
    String teamName;
    String stadiumName;
    Timestamp teamCreatedAt;
}
