package model.player;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Position {
    FB("1루수"), SB("2루수"), TB("3루수"),
    C("포수"), P("투수"), SS("유격수"),
    LF("좌익수"), CF("중견수"), RF("우익수");


    private final String name;

    Position(String name) {
        this.name = name;
    }

    public static Position findByName(String name) {
        return Arrays.stream(Position.values())
                .filter(position -> position.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    /*
- 1루수 First Baseman (1B)
- 2루수 Second Baseman (2B)
- 3루수 Third Baseman (3B)
- 포수 Catcher (C)
- 투수 Pitcher (P)
- 유격수 Short Stop (SS)
- 좌익수 Left Fielder (LF)
- 중견수 Center Fielder (CF)
- 우익수 Right Fielder (RF)
 */
}
