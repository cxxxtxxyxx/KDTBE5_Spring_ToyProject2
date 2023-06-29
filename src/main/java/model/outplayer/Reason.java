package model.outplayer;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Reason {
    GAMBLE("도박"),
    VIOLENCE("폭행"),
    DRUNK_DRIVING("음주운전"),
    ETC("기타");

    private final String name;

    Reason(String name) {
        this.name = name;
    }

    public static Reason findByName(String name) {
        return Arrays.stream(Reason.values())
                .filter(reason -> reason.name.equals(name))
                .findFirst()
                .orElse(ETC);
    }
}
