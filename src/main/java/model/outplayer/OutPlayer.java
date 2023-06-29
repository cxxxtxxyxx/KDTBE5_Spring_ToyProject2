package model.outplayer;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class OutPlayer {
    private int id;
    private int playerId;
    private Reason reason;
    private Timestamp createdAt;
}
