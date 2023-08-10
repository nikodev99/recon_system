package who.reconsystem.app.models;

import lombok.Getter;

@Getter
public enum Operator {
    AND, OR, GREATER_THAN, LESS_THAN, EQUALS, BETWEEN, NONE;

    private String operator;

    static {
        AND.operator = "AND";
        OR.operator = "OR";
        GREATER_THAN.operator = ">";
        LESS_THAN.operator = "<";
        EQUALS.operator = "=";
        BETWEEN.operator = "BETWEEN";
        NONE.operator = "NONE";
    }
}
