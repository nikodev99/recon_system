package who.reconsystem.app.models;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.*;
import java.util.stream.Collectors;

public class QueryBuilder implements Query {
    private String query;

    private String table;

    @Inject
    public QueryBuilder(@Named("Table") String table) {
        this.table = table;
    }

    @Override
    public Query insert() {
        query = "INSERT ";
        return this;
    }

    @Override
    public Query insert(List<String> fields) {
        query = "INSERT INTO " + table + insertedExploded(fields);
        return this;
    }

    @Override
    public Query into() {
        query += "INTO " + table;
        return this;
    }

    @Override
    public Query insertedFields(List<String> fields) {
        query += " (" + join(fields) + ") ";
        return this;
    }

    @Override
    public Query insertedValues(List<Object> values) {
        List<String> valueList = values.stream().map(Object::toString)
                .collect(Collectors.toList());
        query += "VALUES " + "(" + join(valueList) + ")";
        return this;
    }

    @Override
    public Query insertedFieldAndValue(Map<String, Object> fieldsAndValues) {
        List<String> fields = new ArrayList<>(fieldsAndValues.keySet());
        List<String> values = fieldsAndValues.values().stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        query += " (" + join(fields) + ") VALUES (" + join(values) + ")";
        return this;
    }

    @Override
    public Query update() {
        query = "UPDATE " + table;
        return this;
    }

    @Override
    public Query updateId(List<String> data) {
        mapValue(data);
        query = "UPDATE " + table + " SET " + join(data) + " WHERE id = ?";
        return this;
    }

    @Override
    public Query updateId(Map<String, Object> data) {
        query = "UPDATE " + table + " SET " + mapValue(data) + " WHERE id = ?";
        return this;
    }

    @Override
    public Query set() {
        query += " SET ";
        return this;
    }

    @Override
    public Query set(List<String> fields) {
        mapValue(fields);
        query += " SET " + join(fields);
        return this;
    }

    @Override
    public Query set(Map<String, Object> fieldsAndValue) {
        query += " SET " + mapValue(fieldsAndValue);
        return this;
    }

    @Override
    public Query updateValue(List<String> fields) {
        mapValue(fields);
        query += join(fields);
        return this;
    }

    @Override
    public Query delete() {
        query = deleteStatement(Collections.emptyMap(), Operator.NONE);
        return this;
    }

    @Override
    public Query deleteId() {
        query = deleteStatement(Collections.singletonMap("id", "?"), Operator.NONE);
        return this;
    }

    @Override
    public Query delete(String field) {
        query = deleteStatement(Collections.singletonMap(field, "?"), Operator.NONE);
        return this;
    }

    @Override
    public Query delete(String field, Object value) {
        query = deleteStatement(Collections.singletonMap(field, value), Operator.NONE);
        return this;
    }

    @Override
    public Query delete(Map<String, Object> data, Operator operator) {
        query = deleteStatement(Collections.unmodifiableMap(data), operator);
        return this;
    }

    @Override
    public Query select() {
        query = selectStatement(Collections.emptyList(), false);
        return this;
    }

    @Override
    public Query selectAll() {
        query = selectStatement(Collections.emptyList(), true);
        return this;
    }

    @Override
    public Query select(String field) {
        query = selectStatement(Collections.singletonList(field), false);
        return this;
    }

    @Override
    public Query select(List<String> fields) {
        query = selectStatement(fields, false);
        return this;
    }

    @Override
    public QueryBuilder from() {
        query += " FROM " + table;
        return this;
    }

    @Override
    public Query where() {
        query += whereStatement("WHERE ", Collections.emptyMap(), Operator.NONE);
        return this;
    }

    @Override
    public Query whereId() {
        query += whereStatement("WHERE ", Collections.singletonMap("id", Operator.EQUALS), Operator.NONE);
        return this;
    }

    @Override
    public Query where(String field) {
        query += whereStatement("WHERE ", Collections.singletonMap(field, Operator.EQUALS), Operator.NONE);
        return this;
    }

    @Override
    public Query where(String field, Operator operator) {
        query += whereStatement("WHERE ", Collections.singletonMap(field, operator), Operator.NONE);
        return this;
    }

    @Override
    public Query where(List<String> fields, Operator operator) {
        query += multipleWhere(fields, operator, "WHERE");
        return this;
    }

    @Override
    public Query where(List<String> fields, List<Operator> operators, Operator liaise) {
        Map<String, Operator> map = listToMap(fields, operators);
        query += whereStatement("WHERE ", map, liaise);
        return this;
    }

    @Override
    public Query andWhere(String field) {
        query += whereStatement("AND ", Collections.singletonMap(field, Operator.EQUALS), Operator.NONE);
        return this;
    }

    @Override
    public Query andWhere(String field, Operator operator) {
        query += whereStatement("AND ", Collections.singletonMap(field, operator), Operator.NONE);
        return this;
    }

    @Override
    public Query andWhere(List<String> fields, Operator operator) {
        query += multipleWhere(fields, operator, "AND");
        return this;
    }

    @Override
    public Query andWhere(List<String> fields, List<Operator> operators, Operator liaise) {
        Map<String, Operator> map = listToMap(fields, operators);
        query += whereStatement("AND ", map, liaise);
        return this;
    }

    @Override
    public Query orWhere(String field) {
        query += whereStatement("OR ", Collections.singletonMap(field, Operator.EQUALS), Operator.NONE);
        return this;
    }

    @Override
    public Query orWhere(String field, Operator operator) {
        query += whereStatement("OR ", Collections.singletonMap(field, operator), Operator.NONE);
        return this;
    }

    @Override
    public Query orWhere(List<String> fields, Operator operator) {
        query += multipleWhere(fields, operator, "OR");
        return this;
    }

    @Override
    public Query orWhere(List<String> fields, List<Operator> operators, Operator liaise) {
        Map<String, Operator> map = listToMap(fields, operators);
        query += whereStatement("OR ", map, liaise);
        return this;
    }

    @Override
    public Query like(String field) {
        query += field + " LIKE ?";
        return this;
    }

    @Override
    public Query like(String field, String value) {
        query += field + " LIKE '%" + value + "%'";
        return this;
    }

    @Override
    public Query headLike(String field, String value) {
        query += field + " LIKE '%" + value + "'";
        return this;
    }

    @Override
    public Query tailLike(String field, String value) {
        query += field + " LIKE '" + value + "%'";
        return this;
    }

    @Override
    public Query order(String field, Ordering order) {
        return order(Collections.singletonList(field), Collections.singletonList(order));
    }

    @Override
    public Query order(List<String> fields, List<Ordering> orderings) {
        List<String> columns = new ArrayList<>();
        if (fields.size() == orderings.size()) {
            for (int i = 0; i < fields.size(); i++) {
                columns.add(fields.get(i) + " " + orderings.get(i));
            }
        }
        query += " ORDER BY " + join(columns);
        return this;
    }

    @Override
    public Query limit(int recordLimitedNumber) {
        query += " LIMIT " + recordLimitedNumber;
        return this;
    }

    @Override
    public String query() {
        return this.query;
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String getTable() {
        return table;
    }

    private String join(List<String> data) {
        return join(", ", data);
    }

    private String join(String delimiter, List<String> data) {
        return String.join(delimiter, data);
    }

    private void mapValue(List<String> data) {
        data.replaceAll(s -> s + " " + Operator.EQUALS .getOperator()+ " ?");
    }

    private String mapValue(Map<String, Object> data) {
        List<String> mappedValue = new ArrayList<>();
        data.forEach((key, value) -> mappedValue.add(key + " " + Operator.EQUALS.getOperator() + " " + value));
        return join(mappedValue);
    }

    private String multipleWhere(List<String> fields, Operator operator, String sqlStatement) {
        List<String> statements = new ArrayList<>();
        String statement = " " + sqlStatement;
        if (fields.size() == 1 && operator.getOperator().equals("OR")) {
            statement += " " + fields.get(0) + " = ? OR "  + fields.get(0) + " = ?";
        }else {
            fields.forEach(field -> statements.add(field + " = ?"));
            statement += " " + join(" "+ operator.getOperator() + " ", statements);
        }
        return statement;
    }

    private String insertedExploded(List<String> fields) {
        List<String> values = new ArrayList<>();
        fields.forEach(value -> values.add(value.replace(value, "?")));
        String field_exploded = this.join(fields);
        String value_exploded = this.join(values);
        return " (" + field_exploded + ") VALUES (" + value_exploded + ")";
    }

    private String deleteStatement(Map<String, Object> data, Operator operator) {
        List<String> values = new ArrayList<>();
        String statement = "DELETE FROM " + table;
        if (!data.isEmpty()) {
            statement += " WHERE ";
            data.forEach((key, value) -> values.add(key + " = " + value));
            if (values.size() > 1 && !operator.getOperator().equals("NONE")) {
                statement += join(" " + operator.getOperator() + " ", values);
            }else {
                statement += join(values);
            }
        }
        return statement;
    }

    private String selectStatement(List<String> fields, boolean isAll) {
        String select = "SELECT ";
        if (isAll) {
            select += "* FROM " + table;
        }else {
            if (!fields.isEmpty()) {
                select += join(fields);
            }else {
                select += "*";
            }
        }
        return select;
    }

    private String whereStatement(String sqlStatement, Map<String, Operator> fieldsToOperate, Operator liaise) {
        List<String> sql = new ArrayList<>();
        String statement = " " + sqlStatement;
        if (!fieldsToOperate.isEmpty() && liaise.getOperator().equals("OR")) {
            fieldsToOperate.forEach((k, v) -> sql.add("(" + k + " " + v.getOperator() + " ? " + liaise.getOperator() + " "  + k + " " + v.getOperator() + " ?)"));
        }else {
             fieldsToOperate.forEach((k, v) -> {
                if (v.getOperator().equals("BETWEEN"))
                    sql.add(k + " " + v.getOperator() + " ? " + liaise.getOperator() + " ?");
                else
                    sql.add(k + " " + v.getOperator() + " ?");
            });
        }
        statement += join(" "+ liaise.getOperator() + " ", sql);
        return statement;
    }

    private Map<String, Operator> listToMap(List<String> list, List<Operator> operators) {
        Map<String, Operator> map = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String field = list.get(i);
            Operator operator = operators.get(i);
            map.put(field, operator);
        }
        return map;
    }
}

