package util;

import lombok.Getter;

@Getter
public enum QueryExecutionStatus {

    SUCCESS("성공", 1),
    FAIL("실패", 0);

    private final String message;
    private final int symbol;

    QueryExecutionStatus(String message, int symbol) {
        this.message = message;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return message;
    }
}
