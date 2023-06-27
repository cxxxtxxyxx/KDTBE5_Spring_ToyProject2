package model.stadium;

import lombok.Builder;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@ToString
public class Stadium {
    private int id;
    private String name;
    private Timestamp createdAt;
}
