package who.reconsystem.app.models;

import java.util.List;
import java.util.Map;

public interface Query {
    Query insert();

    Query insert(List<String> fields);

    Query into();

    Query insertedFields(List<String> fields);

    Query insertedValues(List<Object> values);

    Query insertedFieldAndValue(Map<String, Object> fieldsAndValues);

    Query update();

    Query updateId(List<String> data);

    Query updateId(Map<String, Object> data);

    Query set();

    Query set(List<String> fields);

    Query set(Map<String, Object> fieldsAndValue);

    Query updateValue(List<String> fields);

    Query delete();

    Query deleteId();

    Query delete(String field);

    Query delete(String field, Object value);

    Query delete(Map<String, Object> data, Operator operator);

    Query select();

    Query selectAll();

    Query select(String field);

    Query select(List<String> fields);

    Query from();

    Query where();

    Query whereId();

    Query where(String field);

    Query where(String field, Operator operator);

    Query where(List<String> fields, Operator operator);

    Query where(List<String> fields, List<Operator> operators, Operator liaise);

    Query andWhere(String field);

    Query andWhere(String field, Operator operator);

    Query andWhere(List<String> fields, Operator operator);

    Query andWhere(List<String> fields, List<Operator> operators, Operator liaise);

    Query orWhere(String field);

    Query orWhere(String field, Operator operator);

    Query orWhere(List<String> fields, Operator operator);

    Query orWhere(List<String> fields, List<Operator> operators, Operator liaise);

    Query like(String field);

    Query like(String field, String value);

    Query headLike(String field, String value);

    Query tailLike(String field, String value);

    Query order(String field, Ordering order);

    Query order(List<String> fields, List<Ordering> orderings);

    Query limit(int recordLimitedNumber);

    String query();

    String getTable();

    void setTable(String table);
}
