package who.reconsystem.app.models;

import com.google.inject.Inject;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.models.connect.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Table {

    protected Query query;
    protected Connection connect;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet result;

    @Inject
    public Table(Query query, DbConnect connect) {
        this.query = query;
        this.connect = connect.connectSQLite();
    }

    /**
     * Insert data to a database when specifying fields and values.
     * @param fieldAndValues Map<String, Object> - the fields are all Strings and values are Objects.
     * @return long - The row count for SQL Data Manipulation Language (DML) statements
     */
    public long insert(Map<String, Object> fieldAndValues) {
        List<String> fields = new ArrayList<>(fieldAndValues.keySet());
        List<Object> values = new ArrayList<>(fieldAndValues.values());
        String insertedStatement = insertRequest(fields);
        return requestResponse(values, insertedStatement);
    }

    /**
     * Insert data to a database when specifying just the values.
     * We assume that the fields are known
     * @param values List<String> - the values list of each field.
     * @return long - The row count for SQL Data Manipulation Language (DML) statements
     */
    public long insert(List<Object> values) {
        String insertedStatement = insertRequest();
        return requestResponse(values, insertedStatement);
    }

    /**
     * Find all the record from the database table by specifying the fields we want.
     * @param fields List<String> - the fields of records we want.
     * @return List<String> - List of all the records.
     */
    public List<String[]> findAll(List<String> fields) {
        List<String[]> allData = new ArrayList<>();
        String sqlRequest = getAllRequest(fields);
        try {
            allData = getRecords(sqlRequest);
        }catch (SQLException sqlException) {
            DialogMessage.exceptionDialog(sqlException);
        }
        stopConnection();
        return Collections.unmodifiableList(allData);
    }

    /**
     * Find all the record from the database table with all the fields.
     * @return List<String> - List of all the records.
     */
    public List<String[]> findAll() {
        List<String[]> allData = new ArrayList<>();
        String sqlRequest = getAllRequest();
        try {
            allData = getRecords(sqlRequest);
        }catch (SQLException sqlException) {
            DialogMessage.exceptionDialog(sqlException);
        }
        stopConnection();
        return Collections.unmodifiableList(allData);
    }

    /**
     * Find one record by specifying a list of values belonging to a record to search.
     * @param values List<Object> - The values belonging to a record to search.
     * @return List<String> - The record searched.
     */
    public List<String> find(List<Object> values) {
        List<String> data = new ArrayList<>();
        String sqlRequest = getRequest();
        try {
            data = getOneRecord(sqlRequest, values);
        }catch (SQLException sqlException) {
            DialogMessage.exceptionDialog(sqlException);
        }
        stopConnection();
        return Collections.unmodifiableList(data);
    }

    /**
     * Find one record by specifying one value belonging to a record to search.
     * @param value Object - The values belonging to a record to search.
     * @return List<String> - The record searched.
     */
    public List<String> find(Object value) {
        return find(Collections.singletonList(value));
    }

    /**
     * Update a record by specifying the fields to update and its new values
     * @param fieldsAndValues Map<String, Object> - The fields [String] and its values [Object]
     * @return long - The id of the record updated.
     */
    public long update(Map<String, Object> fieldsAndValues) {
        List<String> fields = new ArrayList<>(fieldsAndValues.keySet());
        List<Object> values = new ArrayList<>(fieldsAndValues.values());
        String sqlRequest = updateRequest(fields);
        return requestResponse(values, sqlRequest);
    }

    /**
     * Update a record by specifying the values.
     * We assume that the fields are known
     * @param values List<Object> - List of the new values that will replace the old ones
     * @return long - The id of the record updated.
     */
    public long update(List<Object> values) {
        String sqlRequest = updateRequest();
        System.out.println(sqlRequest);
        return requestResponse(values, sqlRequest);
    }

    /**
     * Shows if a record exist or not by specifying the values belonging to that record.
     * @param values List<Object> the values belonging to a record to search.
     * @return boolean - true if it exists, false otherwise.
     */
    public boolean itExists(List<Object> values) {
        List<String> record = find(values);
        return record.isEmpty();
    }

    /**
     * Shows if a record exist or not by specifying its id.
     * @param value Object - The id of the record.
     * @return boolean - true if it exists, false otherwise.
     */
    public boolean idExists(Object value) {
        List<String> record = find(value);
        return record.isEmpty();
    }

    public long delete(List<Object> values) {
        String sqlRequest = deleteRequest();
        return requestResponse(values, sqlRequest);
    }

    public long delete(Object value) {
        String sqlRequest = deleteRequest();
        return requestResponse(Collections.singletonList(value), sqlRequest);
    }

    public long deleteAll() {
        String sqlRequest = deleteRequest();
        long responseId = 0;
        try {
            preparedStatement = prepare(sqlRequest);
            responseId = preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            DialogMessage.exceptionDialog(sqlException);
        }
        stopConnection();
        return responseId;
    }

    protected String insertRequest(List<String> values) {
        return query.insert(values).query();
    }

    protected String insertRequest() {
        return query.insert(insertFields()).query();
    }

    protected String getAllRequest(List<String> fields) {
        return query.selectAll().query();
    }

    protected String getAllRequest() {
        return query.selectAll().query();
    }

    protected String getRequest(List<String> fields) {
        return query.select().from().whereId().query();
    }

    protected String getRequest() {
        return query.select().from().whereId().query();
    }

    protected String updateRequest(List<String> fields) {
        return query.updateId(fields).query();
    }

    protected String updateRequest() {
        List<String> fields = updateFields();
        fields.remove(fields.size() - 1);
        return query.updateId(fields).query();
    }

    protected String deleteRequest() {
        return query.deleteId().query();
    }

    protected void setValues(List<Object> values) throws SQLException {
        for(int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(i + 1, values.get(i));
        }
    }

    protected List<String> populateEntity() throws SQLException {
        List<String> fields = allFields();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            String strData = result.getString(i + 1);
            data.add(strData);
        }
        return data;
    }

    protected List<String> allFields() {
        return Collections.emptyList();
    }

    protected List<String> insertFields() {
        return Collections.emptyList();
    }

    protected List<String> updateFields() {
        return Collections.emptyList();
    }

    protected List<String> specificList() {
        return Collections.emptyList();
    }

    protected PreparedStatement prepare(String sql) throws SQLException {
        return connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    protected Statement prepare() throws SQLException {
        return connect.createStatement();
    }

    protected void stopConnection() {
            try {
                connect.close();
            }catch (SQLException sqlException) {
                DialogMessage.exceptionDialog(sqlException);
            }
    }

    private long requestResponse(List<Object> values, String sqlRequest) {
        long responseId = 0;
        try {
            preparedStatement = prepare(sqlRequest);
            setValues(values);
            responseId = preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
            //DialogMessage.exceptionDialog(sqlException);
        }
        stopConnection();
        return responseId;
    }

    private List<String[]> getRecords(String sqlRequest, List<Object> values) throws SQLException {
        List<String[]> allData = new ArrayList<>();
        preparedStatement = prepare(sqlRequest);
        if (!values.isEmpty()) setValues(values);
        result = preparedStatement.executeQuery();
        while (result.next()) {
            List<String> data = populateEntity();
            allData.addAll(result.getRow() - 1, Collections.singleton(data.toArray(new String[0])));
        }
        return Collections.unmodifiableList(allData);
    }

    private List<String[]> getRecords(String sqlRequest) throws SQLException {
        return getRecords(sqlRequest, Collections.emptyList());
    }

    private List<String> getOneRecord(String sqlRequest, List<Object> values) throws SQLException {
        List<String> data = new ArrayList<>();
        preparedStatement = prepare(sqlRequest);
        setValues(values);
        result = preparedStatement.executeQuery();
        while (result.next()) {
            data = populateEntity();
        }
        return Collections.unmodifiableList(data);
    }
}